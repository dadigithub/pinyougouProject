app.service('loginService',function ($http) {

    //获取登录人姓名
    this.loginName=function () {
        return $http.get('../login/name.do');
    }

})