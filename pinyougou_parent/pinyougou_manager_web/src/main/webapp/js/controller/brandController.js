//2.创建控制器
app.controller("brandController", function ($scope, $http,$controller,brandService) {

    //继承
    /*
       第一个$scope是父类的
       第二个$scope是当前controller的
         这句话的作用是:
            将父类baseController中的$scope赋值给当前$scope属性
     */
    $controller("baseController",{$scope:$scope});


    //查询品牌列表
    $scope.findAll=function () {
        brandService.findAll().success(
            function (response) {
                //请求地址响应的数据,赋值给list变量,list变量再进行遍历拿出响应的数据
                $scope.list=response;
            }
        );
    }





    //分页
    $scope.findPage = function (page, size) {
        brandService.findPage(page,size).success( //success()方法的作用是响应成功之后,对响应的数据进行赋值遍历
            function (response) {
                $scope.list=response.rows;//显示当前页数据(当前页的内容)
                //把后端传过来的total传递给前端的分页插件,让插件进行处理,更加灵活的得到所需要的值
                $scope.paginationConf.totalItems=response.total; //数据总记录数/条数
            }
        );
    };



    //添加
    $scope.save = function () {     /*$scope.entity双向绑定变量名 input标签中输入的值会自动封装到entity中*/
        //定义地址变量
        var object = null;
        if ($scope.entity.id != null) {
            object = brandService.update($scope.entity);
        } else {
            object=brandService.add($scope.entity)
        }
        //对添加/修改地址进行拼接,定义变量把变量拼接到请求地址当中,使得地址更加灵活
        object.success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();     //刷新列表
                } else {
                    alert(response.message); //添加失败提示信息
                }
            }
        );
    };




    //查询品牌实体,根据id查询
    $scope.findOne = function (id) {
        brandService.findOne(id).success(
            function (response) {
                $scope.entity=response;
            }
        );
    }




    //删除
    $scope.dele=function () {
        if(confirm('确定要删除吗？')) {
            brandService.dele($scope.selectIds).success(
                function (response) {
                    //如果删除成功,刷新列表
                    if (response.success) {
                        $scope.reloadList();//刷新列表
                    } else {  //如果删除失败,弹出消息框
                        alert(response.message);
                    }
                }
            );
        }
    }



    $scope.searchEntity={};

    //品牌查询
    $scope.search=function (page,size) { //在进行品牌查询时,除了我们要把page,size传递过去,还需要把模糊查询的条件也通过post请求传递给服务器
        brandService.search(page,size,$scope.searchEntity).success( //success()方法的作用是响应成功之后,对响应的数据进行赋值遍历
            function (response) {
                $scope.list=response.rows;//显示当前页数据(当前页的内容)
                //把后端传过来的total传递给前端的分页插件,让插件进行处理,更加灵活的得到所需要的值
                $scope.paginationConf.totalItems=response.total; //数据总记录数/条数
            }
        );
    }


});