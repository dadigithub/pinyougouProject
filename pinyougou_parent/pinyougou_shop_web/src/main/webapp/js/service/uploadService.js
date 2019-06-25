//文件上传服务层
app.service("uploadService",function ($http) {
    //上传文件
    this.uploadFile=function () {
        //代表上传的表单数据,以2进制格式上传
        var formData = new FormData();
        //formData表示上传的文件
        formData.append("file",file.files[0]); //,之后的file 表示文件上传框的name,前端的name值必须为file
        return $http({
           method:'POST',
           url:"../upload.do",
           data:formData,
           headers:{'Content-Type':undefined},
           transformRequest:angular.identity
        }); //headers:{'Content-Type':undefined},如果是上传操作,则文件的类型为未定义的格式,如果不定义默认为json格式字符串的格式
    }      //transformRequest:angular.identity对表单进行二进制序列化


});