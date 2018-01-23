package biz.dealnote.messenger.api.services;

import biz.dealnote.messenger.api.model.Items;
import biz.dealnote.messenger.api.model.VKApiVideo;
import biz.dealnote.messenger.api.model.VKApiVideoAlbum;
import biz.dealnote.messenger.api.model.response.BaseResponse;
import biz.dealnote.messenger.api.model.response.DefaultCommentsResponse;
import biz.dealnote.messenger.api.model.response.SearchVideoResponse;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by admin on 21.12.2016.
 * phoenix
 */
public interface IVideoService {

    @FormUrlEncoded
    @POST("video.getComments")
    Single<BaseResponse<DefaultCommentsResponse>> getComments(@Field("owner_id") Integer ownerId,
                                                              @Field("video_id") int videoId,
                                                              @Field("need_likes") Integer needLikes,
                                                              @Field("start_comment_id") Integer startCommentId,
                                                              @Field("offset") Integer offset,
                                                              @Field("count") Integer count,
                                                              @Field("sort") String sort,
                                                              @Field("extended") Integer extended,
                                                              @Field("fields") String fields);

    @FormUrlEncoded
    @POST("video.add")
    Single<BaseResponse<Integer>> addVideo(@Field("target_id") Integer targetId,
                                           @Field("video_id") Integer videoId,
                                           @Field("owner_id") Integer ownerId);

    @FormUrlEncoded
    @POST("video.delete")
    Single<BaseResponse<Integer>> deleteVideo(@Field("video_id") Integer videoId,
                                              @Field("owner_id") Integer ownerId,
                                              @Field("target_id") Integer targetId);

    @FormUrlEncoded
    @POST("video.getAlbums")
    Single<BaseResponse<Items<VKApiVideoAlbum>>> getAlbums(@Field("owner_id") Integer ownerId,
                                                           @Field("offset") Integer offset,
                                                           @Field("count") Integer count,
                                                           @Field("extended") Integer extended,
                                                           @Field("need_system") Integer needSystem);

    @FormUrlEncoded
    @POST("video.search")
    Single<BaseResponse<SearchVideoResponse>> search(@Field("q") String query,
                                                     @Field("sort") Integer sort,
                                                     @Field("hd") Integer hd,
                                                     @Field("adult") Integer adult,
                                                     @Field("filters") String filters,
                                                     @Field("search_own") Integer searchOwn,
                                                     @Field("offset") Integer offset,
                                                     @Field("longer") Integer longer,
                                                     @Field("shorter") Integer shorter,
                                                     @Field("count") Integer count,
                                                     @Field("extended") Integer extended);

    @FormUrlEncoded
    @POST("video.restoreComment")
    Single<BaseResponse<Integer>> restoreComment(@Field("owner_id") Integer ownerId,
                                                @Field("comment_id") int commentId);

    @FormUrlEncoded
    @POST("video.deleteComment")
    Single<BaseResponse<Integer>> deleteComment(@Field("owner_id") Integer ownerId,
                                                @Field("comment_id") int commentId);

    @FormUrlEncoded
    @POST("video.get")
    Single<BaseResponse<Items<VKApiVideo>>> get(@Field("owner_id") Integer ownerId,
                                                @Field("videos") String videos,
                                                @Field("album_id") Integer albumId,
                                                @Field("count") Integer count,
                                                @Field("offset") Integer offset,
                                                @Field("extended") Integer extended);

    @FormUrlEncoded
    @POST("video.createComment")
    Single<BaseResponse<Integer>> createComment(@Field("owner_id") Integer ownerId,
                                                @Field("video_id") int videoId,
                                                @Field("message") String message,
                                                @Field("attachments") String attachments,
                                                @Field("from_group") Integer fromGroup,
                                                @Field("reply_to_comment") Integer replyToComment,
                                                @Field("sticker_id") Integer stickerId,
                                                @Field("guid") Integer uniqueGeneratedId);


    @FormUrlEncoded
    @POST("video.editComment")
    Single<BaseResponse<Integer>> editComment(@Field("owner_id") Integer ownerId,
                                              @Field("comment_id") int commentId,
                                              @Field("message") String message,
                                              @Field("attachments") String attachments);
}
