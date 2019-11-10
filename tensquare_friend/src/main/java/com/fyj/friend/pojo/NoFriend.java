package com.fyj.friend.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;


@IdClass(com.fyj.friend.pojo.Friend.class)
@Entity
@Table(name = "tb_nofriend")
public class NoFriend implements Serializable {

        @Id
        private String userid;

        @Id
        private String friendid;

        private String islike;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getFriendid() {
            return friendid;
        }

        public void setFriendid(String friendid) {
            this.friendid = friendid;
        }

        public String getIslike() {
            return islike;
        }

        public void setIslike(String islike) {
            this.islike = islike;
        }

        public NoFriend() {
        }

        public NoFriend(String userid, String friendid, String islike) {
            this.userid = userid;
            this.friendid = friendid;
            this.islike = islike;
        }
}