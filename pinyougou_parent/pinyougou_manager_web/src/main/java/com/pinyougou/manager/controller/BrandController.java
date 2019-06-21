package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController //他相当于 @Controller+@ResponseBody,表示服务器以json格式的字符串响应给客户端
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;


    /**
     * 查询品牌所有信息
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbBrand> findAll() {
        return brandService.findAll();
    }


    /**
     * 品牌分页
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int size) {
        return brandService.findPage(page,size);
    }




    /**
     * 添加品牌信息
     * @param brand
     * @return
     */
    @RequestMapping("/add")
    //@RequestBody 1.表示前端请求方式为post,2.请求体为json格式
    public Result add(@RequestBody TbBrand brand) {
        try {
            brandService.add(brand);
            //如果添加成功,将成功的结果封装到自定义Result中,以json格式响应给客户端
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            //如果添加失败,将失败的结果封装到自定义Result中,以json格式响应给客户端
            return new Result(false,"添加失败");
        }
    }



    /**
     * 根据品牌id查询品牌信息
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public TbBrand findOne(Long id) {
        return brandService.findOne(id);
    }




    /**
     * 修改品牌信息
     * @param brand
     * @return
     */
    @RequestMapping("/update")
    //规定前端要以1.post请求,2.json格式字符串发送请求体
    public Result update(@RequestBody TbBrand brand) {
        try {
            brandService.update(brand);
            //如果修改成功,将成功的结果封装到自定义Result中,以json格式响应给客户端
            return  new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            //如果修改失败,将失败的结果封装到自定义Result中,以json格式响应给客户端
            return new Result(false,"修改失败");
        }
    }


    /**
     * 根据ids [] 删除品牌信息
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[]  ids) {
        try {
            brandService.delete(ids);
            //如果删除成功,将删除成功的结果封装到自定义Result中,以json格式响应给客户端
            return  new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            //如果删除成功,将删除成功的结果封装到自定义Result中,以json格式响应给客户端
            return new Result(false,"删除失败");
        }
    }




    /**
     * 模糊查询品牌信息
     * @param brand
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbBrand brand, int page, int size) {
        return brandService.findPage(brand,page,size);
    }




    /**
     * 返回下拉列表数据
     * @return
     */
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return brandService.selectOptionList();
    }






}
