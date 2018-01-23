package biz.dealnote.messenger.api.model;

import java.util.ArrayList;
import java.util.List;

public class VKApiNews {

    public String type; //friends_recomm //post

    public int source_id;
    public long date;
    public int post_id;
    public String post_type;
    public boolean final_post;
    public int copy_owner_id;
    public int copy_post_id;
    public List<VKApiPost> copy_history;
    public long copy_post_date;
    public String text;
    public boolean can_edit;
    public boolean can_delete;
    public int comment_count;
    public boolean comment_can_post;
    public int like_count;
    public boolean user_like;
    public boolean can_like;
    public boolean can_publish;
    public int reposts_count;
    public boolean user_reposted;

    /**
     * Information about attachments to the post (photos, links, etc.), if any;
     */
    public VkApiAttachments attachments;

    public VKApiPlace geo;

    public List<VKApiPhoto> photos;
    public List<VKApiPhoto> photo_tags;
    public List<VKApiNote> notes;
    public ArrayList<String> friends;

    public int views;

    public VKApiNews() {

    }

    public int getAttachmentsCount(){
        return attachments == null ? 0 : attachments.size();
    }

    public boolean hasAttachments(){
        return getAttachmentsCount() > 0;
    }

    public boolean hasCopyHistory(){
        return copy_history != null && copy_history.size() > 0;
    }
}