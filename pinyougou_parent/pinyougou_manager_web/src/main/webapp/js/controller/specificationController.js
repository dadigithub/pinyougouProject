 //控制层  控制层继承baseController,调用service层
app.controller('specificationController' ,function($scope,$controller   ,specificationService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		specificationService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		specificationService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		specificationService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  在js中.之前的变量都需要被初始化
		if($scope.entity.specification.id!=null){//如果有ID
			serviceObject=specificationService.update( $scope.entity ); //修改  
		}else{
			serviceObject=specificationService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		specificationService.dele( $scope.selectIds ).success(
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
		specificationService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}



	/*   对变量进行定义,前端新增规格选项表输入的optionName,orders两个属性的值,会添加到这个数组中
	  为什么要这样定义?因为,他包含两个变量,1.entity 2.specificationOptionList,都需要定义
	*/

     //$scope.entity={specificationOptionList:[]};


	//增加规格选项行

	/*
	       规格增加功能,包含两部分,1.specification,2.specificationOptionList
	     他们都存储在entity中,存储格式为:{specification:{},specificationOptionList:[]}
	 */
	$scope.addTableRow=function () {
		//前端新增规格选项表输入的optionName,orders两个属性的值,添加到entity集合中的specificationOptionList数组中
		$scope.entity.specificationOptionList.push({});
    }


    //删除规格选项行
	$scope.deleTableRow=function (index) {//specificationOptionList是一个数组,要根据索引删除
		$scope.entity.specificationOptionList.splice(index,1);//1.index:移除的位置,2.删除的个数
    }










    
});	
