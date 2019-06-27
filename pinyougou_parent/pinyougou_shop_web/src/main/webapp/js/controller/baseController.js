
//controller层中抽取共性的模块,放在这里,让controller子类继承该类

app.controller("baseController",function ($scope) {

    //分页控件配置 currentPage:当前页的页数  totalItems:总记录数  itemsPerPage:每页记录数(条数) perPageOptions:分页选项
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function () {
            $scope.reloadList();
        } // onChange:当页码变更后自动触发的方法,也就是客户端点击页码,页码发生变化后,自动触发该方法
    };





    //刷新列表
    $scope.reloadList = function () {
        //$scope是控制层和视图层之间数据交换的桥梁
        //将分页控件中的currentPage(当前页数)和itemsPerPage(每页记录数)赋值给findPage
        $scope.search( $scope.paginationConf.currentPage,$scope.paginationConf.itemsPerPage)

    };




    //定义用户勾选的id集合
    $scope.selectIds=[];

    //用户勾选复选框
    $scope.updateSelection = function ($event,id) {

        //表示input标签只有在选中时才为true,也就是只有在勾选状态下才向selectIds[]数组中添加id
        //向数组中添加id(input标签勾选)
        if ($event.target.checked) {

            $scope.selectIds.push(id); //push向集合添加元素

        } else { //向数组中删除id(input标签取消勾选)

            var index = $scope.selectIds.indexOf(id); //查找id在数组中的位置
            $scope.selectIds.splice(index,1); //参数1:移除的位置  参数2:移除的个数

        }
    }



    //提取json字符串数据中某个属性,返回拼接字符串,逗号分隔
    $scope.jsonToString = function (jsonString, key) {

        var json = JSON.parse(jsonString); //将json字符串转换为json对象
        var value = "";
        for (var i=0;i<json.length;i++ ){

            if (i > 0) {
                value+=","
            }
            value+=json[i][key];
        }
        return value;
    }


    //从集合中按照 key 查询对象
    $scope.searchObjectByKey=function(list,key,keyValue){
        for(var i=0;i<list.length;i++){
            if(list[i][key]==keyValue){
                return list[i];
            }
        }
        return null;
    }




});