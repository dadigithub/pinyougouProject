 //控制层 
app.controller('goodsController' ,function($scope,$controller,$location,goodsService,uploadService,itemCatService,typeTemplateService){
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		goodsService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		goodsService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){

		//search方法表示获取地址栏上所有的参数,他是一个数组的形式
	    var id = $location.search()['id']; //获取参数值

        if (id == null) {
        	return;
        }

		goodsService.findOne(id).success(
			function(response){
				$scope.entity= response;

				//向富文本编辑器添加商品介绍
				editor.html($scope.entity.goodsDesc.introduction);

				//显示图片列表
				$scope.entity.goodsDesc.itemImages=JSON.parse($scope.entity.goodsDesc.itemImages);

				//显示商品扩展属性
                $scope.entity.goodsDesc.customAttributeItems=JSON.parse($scope.entity.goodsDesc.customAttributeItems);

                //规格
                $scope.entity.goodsDesc.specificationItems=JSON.parse($scope.entity.goodsDesc.specificationItems);





			}
		);				
	}



	//保存 
	$scope.save=function(){
		//提取文本编辑器的值
        $scope.entity.goodsDesc.introduction=editor.html();

		var serviceObject;//服务层对象  				
		if($scope.entity.goods.id!=null){//如果有ID
			serviceObject=goodsService.update( $scope.entity ); //修改  
		}else{
			serviceObject=goodsService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
                    alert("新增成功");
                    //重新查询
		        	// $scope.reloadList();//重新加载
                    location.href='goods.html';
				}else{
					alert(response.message);
				}
			}		
		);				
	}


    //增加商品
    $scope.add=function(){
        $scope.entity.goodsDesc.introduction=editor.html();

        goodsService.add( $scope.entity  ).success(
            function(response){
                if(response.success){
                    alert("新增成功");
                    $scope.entity={};
                    editor.html("");//清空富文本编辑器
                }else{
                    alert(response.message);
                }
            }
        );
    }




    //批量删除
	$scope.dele=function(){			
		//获取选中的复选框			
		goodsService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		goodsService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}


	//上传文件
	$scope.uploadFile=function () {

        uploadService.uploadFile().success(function (response) {
            if (response.success) {//如果上传成功,取出url
                $scope.image_entity.url = response.message;//设置文件地址
            } else {
            	alert(response.message);
            }
        });
    }



    //上传图片
    $scope.uploadFile=function(){
        uploadService.uploadFile().success(
            function(response){
                if(response.success){
                    $scope.image_entity.url= response.message;
                }else{
                    alert(response.message);
                }
            }
        );
    }


    $scope.entity={goods:{},goodsDesc:{itemImages:[]}}; //定义页面实体结构


	//添加图片列表
	$scope.add_image_entity=function () {
		$scope.entity.goodsDesc.itemImages.push($scope.image_entity);
    }


    //列表中移除图片
	$scope.remove_image_entity=function (index) {
		$scope.entity.goodsDesc.itemImages.splice(index,1);
    }


    //读取一级分类
	$scope.selectItemCat1List=function () {
		//初始值为0,首页的id设置为0
        itemCatService.findByParentId(0).success(
        	function (response) {
				$scope.itemCat1List=response;
            }
		)
    }


    //读取二级分类							//newValue代表监控中变量的变化的值
	$scope.$watch('entity.goods.category1Id',function (newValue,oldValue) {
		//根据前端用户选择的值,查询二级分类
        itemCatService.findByParentId(newValue).success(
        	function (response) {
				$scope.itemCat2List=response;
            }
		);
    });


	//读取三级分类
	$scope.$watch('entity.goods.category2Id',function (newValue, oldValue) {
		//根据前端用户选择的值,查询三级分类
        itemCatService.findByParentId(newValue).success(
        	function (response) {
				$scope.itemCat3List=response;
            }
		)
    });


	//三级分类选择后,读取模板ID
	$scope.$watch('entity.goods.category3Id',function (newValue, oldValue) {
		//根据三级分类,读取模板ID
        itemCatService.findOne(newValue).success(
        	function (response) {
				$scope.entity.goods.typeTemplateId=response.typeId;//更新模板
            }
		)

    });


	//模板ID选择后,更新品牌列表
	$scope.$watch('entity.goods.typeTemplateId',function (newValue, oldValue) {
		typeTemplateService.findOne(newValue).success(
			function (response) {
				$scope.typeTemplate=response;//获取类型模板
				$scope.typeTemplate.brandIds=JSON.parse($scope.typeTemplate.brandIds);//品牌列表

				//如果没有ID,则加载模板中的扩展数据
				if ($location.search()['id']==null) {
                    $scope.entity.goodsDesc.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems)//扩展属性

				}

                //查询规格列表
                typeTemplateService.findSpecList(newValue).success(
                    function(response){
                        $scope.specList=response;
                    }
                );


			}
		);
    });



	//我们需要将用户选中的选项保存在 tb_goods_desc 表的 specification_items 字段中
    $scope.entity={ goodsDesc:{itemImages:[],specificationItems:[]} };

    $scope.updateSpecAttribute=function($event,name,value){
        var object= $scope.searchObjectByKey(
            $scope.entity.goodsDesc.specificationItems ,'attributeName', name);
        if(object!=null){
            if($event.target.checked ){
                object.attributeValue.push(value);
            }else{//取消勾选
                object.attributeValue.splice( object.attributeValue.indexOf(value ) ,1);//移除选项
                //如果选项都取消了，将此条记录移除
			if(object.attributeValue.length==0){
				$scope.entity.goodsDesc.specificationItems.splice(
					$scope.entity.goodsDesc.specificationItems.indexOf(object),1);
			}
		}
			}else{
				$scope.entity.goodsDesc.specificationItems.push(
					{"attributeName":name,"attributeValue":[value]});
			}
    }






	//商品状态
	$scope.status=['未审核','已审核','审核未通过','关闭'];


	//商品分类列表
	$scope.itemCatList=[];

	//加载商品分类列表
	$scope.findItemCatList=function () {
		itemCatService.findAll().success(
			function (response) {
                for (var i = 0; i<response.length ; i++) {
					$scope.itemCatList[response[i].id]=response[i].name;
                }
            }
		);
    }















});	
