//品牌服务
app.service("brandService",function ($http) {

    this.findAll=function () {
        return $http.get('../brand/findAll.do');
    }

    this.findPage=function (page, rows) {
        return $http.get('../brand/findPage.do?page=' + page + '&size=' + size);
    }

    this.findOne=function (id) {
        return $http.post('../brand/findOne.do?id=' + id, $scope.entity);
    }

    this.add=function (entity) {
        return  $http.post('../brand/add.do', entity)
    }

    this.update=function (entity) {
        return  $http.post('../brand/update.do', entity)
    }

    this.dele = function (ids) {
        return $http.get('../brand/delete.do?ids=' + ids)
    };

    this.search=function (page,size,searchEntity) {
        return  $http.post('../brand/search.do?page=' + page + '&size=' + size,searchEntity)
    }


    /*
           下拉列表数据  因为展示的是品牌的下拉列表,所以在brandService.js中发送请求,
         在typeTemplateController.js中调取服务.

     */
    this.selectOptionList=function(){
        return $http.get('../brand/selectOptionList.do');
    }

});