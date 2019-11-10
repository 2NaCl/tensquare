package com.fyj.spit.base.service;

import com.fyj.spit.base.dao.LabelDao;
import com.fyj.spit.base.pojo.Label;
import com.fyj.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelService {
    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    public void save(Label label) {
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    public List<Label> findAll() {
        return labelDao.findAll();
    }

    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    public void update(Label label) {
        labelDao.save(label);
    }

    public void delete(String labelId) {
        labelDao.deleteById(labelId);
    }

    public List<Label> findSearch(Label label){
        return labelDao.findAll(new Specification<Label>(){
            /**
             *
             * @param root:根对象，就是把条件封装到哪个对象中。where 类名 = label.getId
             * @param query：查询关键字，比如group by，order by
             * @param cb：用来封装条件对象，如果直接返回null，表示不需要任何条件
             *
             * @Param as：用来指定你从root里获取到的对象的某个属性，是属于什么类型的
             * @Param and：拼接我们所有的查询条件
             *
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                //用List存储我们的条件
                if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    //用if来判断是否可以获取我们放在where里面的条件
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    //这句话可以翻译成where labelname like "%小明%"
                    list.add(predicate);

                }
                if(label.getState()!=null && !"".equals(label.getState())){
                    Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }
                Predicate[] parr = new Predicate[list.size()];
                //将我们where的条件的数量以数组的形式放在Predicate里面
                parr = list.toArray(parr);
                //把list中的属性复制到Predicate数组中
                return cb.and(parr);
                //通过and完成条件查询构建
            }
        });
    }


    public Page<Label> pageQuery(Label label, int page, int size){
        //封装了一个分页对象，在springdatajpa中想要实现分页，直接传一个分页对象即可
        Pageable pageable = PageRequest.of(page-1, size);
        return labelDao.findAll(new Specification<Label>(){
            /**
             * 分页查询
             * @param root:根对象，就是把条件封装到哪个对象中。where 类名 = label.getId
             * @param query：查询关键字，比如group by，order by
             * @param cb：用来封装条件对象，如果直接返回null，表示不需要任何条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                //用List存储我们的条件
                if(label.getLabelname()!=null && "".equals(label.getLabelname())==false){
                    //用if来判断是否可以获取我们放在where里面的条件
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    //这句话可以翻译成where labelname like "%小明%"
                    list.add(predicate);

                }
                if(label.getState()!=null && !"".equals(label.getState())){
                    Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }
                Predicate[] parr = new Predicate[list.size()];
                //将我们where的条件的数量以数组的形式放在Predicate里面
                parr = list.toArray(parr);
                //把list中的属性复制到Predicate数组中
                return cb.and(parr);
                //通过and完成条件查询构建
            }
        }, pageable);
    }


}
