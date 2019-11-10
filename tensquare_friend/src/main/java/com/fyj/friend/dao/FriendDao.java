package com.fyj.friend.dao;

import com.fyj.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaSpecificationExecutor<Friend>, JpaRepository<Friend,String> {

    public Friend findByUseridAndFriendid(String userid,String friendid);

    @Query(value = "update tb_friend set isLike=? where userid =? and friendid = ?",nativeQuery = true)
    @Modifying
    public void updateIslike(String isLike, String userid, String friendid);

    @Query(value = "delete from tb_friend where userid=? and friendid = ?",nativeQuery = true)
    @Modifying
    void deleteFriend(String id, String friendid);
}
