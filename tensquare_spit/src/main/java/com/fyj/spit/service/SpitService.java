package com.fyj.spit.service;

import com.fyj.spit.dao.SpitDao;
import com.fyj.spit.pojo.Spit;
import com.fyj.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SpitService {
    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    public Spit findById(String id){
        return spitDao.findById(id).get();
    }

    public void save(Spit spit){
        spit.set_id(idWorker.nextId()+"");
        //初始化数据完善
        spit.setPublishtime(new Date());//发布日期
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        //判断当前吐槽是否有父节点,如果有父节点，那么父节点的吐槽回复数+1
        if(spit.getParentid()!=null && !"".equals(spit.getParentid())){
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.get_id()));
            Update update = new Update();
            update.inc("commit", 1);
            mongoTemplate.updateFirst(query, update,"spit");
        }
        spitDao.save(spit);
    }

    public void update(Spit spit){
        spitDao.save(spit);
    }

    public void deleteById(String id){
        spitDao.deleteById(id);
    }


    public Page<Spit> findByParentId(String parentId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Spit> byParentid = spitDao.findByParentid(parentId, pageable);
        return byParentid;
    }


    /**
     * 点赞
     * @param spitId
     */
    public void thumbp(String spitId) {
        //方式一：效率有问题
//        Spit spit = spitDao.findById(spitId).get();
//        spit.setThumbup(spit.getThumbup() == null ? 0 : spit.getThumbup() + 1);
//        spitDao.save(spit);

        //使用MongoDB命令实现点赞自增 db.spit.update({"_id":"1"},{$inc:{thumbup:NumberInt(1)}})
        Query query = new Query();//对应着更新的限制条件
        query.addCriteria(Criteria.where("_id").is("1"));
        Update update = new Update();//对应着更新的具体方法
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");
    }
}
