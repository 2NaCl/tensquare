package com.fyj.friend.service;

import com.fyj.friend.dao.FriendDao;
import com.fyj.friend.dao.NoFriendDao;
import com.fyj.friend.pojo.Friend;
import com.fyj.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    public int addFriend(String id, String friendid) {
        //先判断userid，friendid是否有数据，有就是重复添加好友，返回0
        Friend byUseridAndFriendid = friendDao.findByUseridAndFriendid(id, friendid);
        if (byUseridAndFriendid != null) {
            return 0;
        }

        //直接添加好友，让好友表中userid到friendid方向的type为0
        Friend friend = new Friend();
        friend.setFriendid(friendid);
        friend.setUserid(id);
        friend.setIslike("0");
        friendDao.save(friend);
        //判断从friend到userid是否有数据，如果有，把双方的状态都改为1
        if (friendDao.findByUseridAndFriendid(id, friendid) != null) {
            //把双方的isLike都改成1
            friendDao.updateIslike("1", id, friendid);
            friendDao.updateIslike("1", friendid, id);
        }
        return 1;
    }

    public int addNoFriend(String id, String friendid) {
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(id, friendid);
        if (noFriend != null) {
            return 0;
        }
        noFriend = new NoFriend();
        noFriend.setUserid(id);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        return 1;
    }


    public void deleteFriend(String id, String friendid) {
        //删除好友，只删除userid到friendid的这条数据
        friendDao.deleteFriend(id, friendid);
        //更新friendid到userid的isLike为0
        friendDao.updateIslike("0", friendid, id);
        //非好友列表中，需要把删除的那个人放进去
        NoFriend noFriend = new NoFriend();
        noFriend.setFriendid(friendid);
        noFriend.setUserid(id);
        noFriendDao.save(noFriend);
    }
}


