package biz.dealnote.messenger.api.services;

import biz.dealnote.messenger.api.model.Items;
import biz.dealnote.messenger.api.model.VkApiFeedList;
import biz.dealnote.messenger.api.model.response.BaseResponse;
import biz.dealnote.messenger.api.model.response.NewsfeedCommentsResponse;
import biz.dealnote.messenger.api.model.response.NewsfeedResponse;
import biz.dealnote.messenger.api.model.response.NewsfeedSearchResponse;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by admin on 27.12.2016.
 * phoenix
 */
public interface INewsfeedService {

    /**
     * filters post, photo, video, topic, market, note
     */
    //https://vk.com/dev/newsfeed.getComments
    @FormUrlEncoded
    @POST("newsfeed.getComments")
    Single<BaseResponse<NewsfeedCommentsResponse>> getComments(@Field("count") Integer count,
                                                               @Field("filters") String filters,
                                                               @Field("reposts") String reposts,
                                                               @Field("start_time") Long startTime,
                                                               @Field("end_time") Long endTime,
                                                               @Field("last_comments_count") Integer lastCommentsCount,
                                                               @Field("start_from") String startFrom,
                                                               @Field("fields") String fields,
                                                               @Field("photo_sizes") Integer photoSizes);

    //https://vk.com/dev/newsfeed.getLists
    @FormUrlEncoded
    @POST("newsfeed.getLists")
    Single<BaseResponse<Items<VkApiFeedList>>> getLists(@Field("list_ids") String listIds,
                                                        @Field("extended") Integer extended);

    /**
     * Returns search results by statuses.
     *
     * @param query     Search query string (e.g., New Year).
     * @param extended  1 — to return additional information about the user or community that placed the post.
     * @param count     Number of posts to return.
     * @param latitude  Geographical latitude point (in degrees, -90 to 90) within which to search.
     * @param longitude Geographical longitude point (in degrees, -180 to 180) within which to search.
     * @param startTime Earliest timestamp (in Unix time) of a news item to return. By default, 24 hours ago.
     * @param endTime   Latest timestamp (in Unix time) of a news item to return. By default, the current time.
     * @param startFrom identifier required to get the next page of results.
     *                  Value for this parameter is returned in next_from field in a reply
     * @param fields    Additional fields of profiles and communities to return.
     * @return Returns the total number of posts and an array of wall objects
     */
    @FormUrlEncoded
    @POST("newsfeed.search")
    Single<BaseResponse<NewsfeedSearchResponse>> search(@Field("q") String query,
                                                        @Field("extended") Integer extended,
                                                        @Field("count") Integer count,
                                                        @Field("latitude") Double latitude,
                                                        @Field("longitude") Double longitude,
                                                        @Field("start_time") Long startTime,
                                                        @Field("end_time") Long endTime,
                                                        @Field("start_from") String startFrom,
                                                        @Field("fields") String fields);

    /**
     * Returns data required to show newsfeed for the current user.
     *
     * @param filters       Filters to apply:
     *                      post — new wall posts
     *                      photo — new photos
     *                      photo_tag — new photo tags
     *                      wall_photo — new wall photos
     *                      friend — new friends
     *                      note — new notes
     *                      List of comma-separated words
     * @param returnBanned  1 — to return news items from banned sources
     * @param startTime     Earliest timestamp (in Unix time) of a news item to return. By default, 24 hours ago.
     * @param endTime       Latest timestamp (in Unix time) of a news item to return. By default, the current time.
     * @param maxPhotoCount Maximum number of photos to return. By default, 5
     * @param sourceIds     Sources to obtain news from, separated by commas.
     *                      User IDs can be specified in formats <uid> or u<uid>
     *                      where <uid> is the user's friend ID.
     *                      Community IDs can be specified in formats -<gid> or g<gid>
     *                      where <gid> is the community ID.
     *                      If the parameter is not set, all of the user's friends and communities
     *                      are returned, except for banned sources, which can be obtained with
     *                      the newsfeed.getBanned method.
     * @param startFrom    identifier required to get the next page of results.
     *                      Value for this parameter is returned in next_from field in a reply
     * @param count         Number of news items to return (default 50; maximum 100). For auto feed,
     *                      you can use the new_offset parameter returned by this method.
     * @param fields        Additional fields of profiles and communities to return.
     * @return Returns an object containing the following fields:
     * items — News array for the current user.
     * profiles — Information about users in the newsfeed.
     * groups — Information about groups in the newsfeed.
     * new_offset — Contains an offset parameter that is passed to get the next array of news.
     * next_from — Contains a from parameter that is passed to get the next array of news.
     */
    @FormUrlEncoded    @POST("newsfeed.get")
    Single<BaseResponse<NewsfeedResponse>> get(@Field("filters") String filters,
                                               @Field("return_banned") Integer returnBanned,
                                               @Field("start_time") Long startTime,
                                               @Field("end_time") Long endTime,
                                               @Field("max_photos") Integer maxPhotoCount,
                                               @Field("source_ids") String sourceIds,
                                               @Field("start_from") String startFrom,
                                               @Field("count") Integer count,
                                               @Field("fields") String fields);

}
