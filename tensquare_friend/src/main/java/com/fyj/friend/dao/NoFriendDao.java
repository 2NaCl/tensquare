package com.fyj.friend.dao;

import com.fyj.friend.pojo.Friend;
import com.fyj.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NoFriendDao extends JpaRepository<NoFriend,String> {

    public NoFriend findByUseridAndFriendid(String userid, String friendid);

    @Query(value = "update tb_friend set isLike=? where userid =? and friendid = ?",nativeQuery = true)
    @Modifying
    public void updateIslike(String isLike, String userid, String friendid);
}
