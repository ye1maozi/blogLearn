package com.learn.blog.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parse2ChildUtil {

    public static<T extends BaseVOForChild> List<T> ParseToChild(List<T> inlist){
//设置children
        List<T> resultList = new ArrayList<>();
        Map<Integer,List<T>> childMap = new HashMap<>();
        Map<Integer,T> allMap = new HashMap<>();
        for (T perm: inlist
        ) {
            if (perm == null ){
                continue;
            }
            if ( perm.getPid() > 0 ){
                //有父类了
                if (allMap.containsKey(perm.getPid())){
                    //父类已经遍历了
                    T parent = allMap.get(perm.getPid());
                    if (parent.getChildren() == null){
                        List<T> tmp = new ArrayList<>();
                        parent.setChildren(tmp);
                    }
                    parent.getChildren().add(perm);
                }else {
                    if (childMap.get(perm.getPid()) == null){
                        //父类等代放入
                        childMap.put(perm.getPid(),new ArrayList<>());
                    }
                    childMap.get(perm.getPid()).add(perm);
                }
            }else{
                resultList.add(perm);
            }
            if (childMap.containsKey(perm.getId())){
                //是否有子类需要放入
                perm.setChildren(childMap.get(perm.getId()));
                childMap.remove(perm.getId());
            }


            allMap.put(perm.getId(),perm);
        }
        for (List<T> value: childMap.values()
        ) {
            //没有认领的children

            resultList.addAll(value);
        }

        return resultList;
    }

}
