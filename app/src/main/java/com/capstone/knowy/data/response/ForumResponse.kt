package com.capstone.knowy.data.response

import com.google.gson.annotations.SerializedName


data class CreateForumResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class AddCommentResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)

data class ForumResponse(

    @field:SerializedName("forums")
    val forums: List<ForumsItem>,

    @field:SerializedName("status")
    val status: String
)

data class ForumsItem(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("comments")
    val comments: List<CommentsItem>,

    @field:SerializedName("forumContent")
    val forumContent: String,

    @field:SerializedName("forumTitle")
    val forumTitle: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("forumId")
    val forumId: String,

    @field:SerializedName("username")
    val username: String
)

data class CommentsItem(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("commentId")
    val commentId: String,

    @field:SerializedName("commentContent")
    val commentContent: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("username")
    val username: String
)

data class ForumDetailResponse(

    @field:SerializedName("forum")
    val forum: Forum,

    @field:SerializedName("status")
    val status: String
)

data class Forum(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("comments")
    val comments: List<CommentsItem>,

    @field:SerializedName("forumContent")
    val forumContent: String,

    @field:SerializedName("forumTitle")
    val forumTitle: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("forumId")
    val forumId: String,

    @field:SerializedName("username")
    val username: String
)

data class CommentResponse(

    @field:SerializedName("comments")
    val comments: List<CommentsItem>,

    @field:SerializedName("status")
    val status: String
)
