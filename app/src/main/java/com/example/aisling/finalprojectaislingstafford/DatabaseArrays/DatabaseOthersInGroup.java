/*
 * Copyright (c)  2017.  Patrick Norton  All Rights Reserved
 * Email: paddy1414@live.ie
 * Github: https://github.com/paddy1414
 * LinkedIn: www.linkedin.com/in/patricknorton9112
 * Youtube: https://www.youtube.com/channel/UCtYIreGkS7cQa_YwVR-Xqyw
 *
 *
 */

package com.example.aisling.finalprojectaislingstafford.DatabaseArrays;

import java.util.ArrayList;

/**
 * Created by Patrick on 01/04/2016.
 */
public class DatabaseOthersInGroup {

    /**
     * The Comment list.
     */
    ArrayList<String> commentList;

    /**
     * Instantiates a new Database comments.
     *
     * @param commentList the comment list
     */
    public DatabaseOthersInGroup(ArrayList<String> commentList) {
        this.commentList = commentList;
    }

    /**
     * Instantiates a new Database comments.
     */
    public DatabaseOthersInGroup() {
        this.commentList = new ArrayList<String>();
    }


    /**
     * Gets comment list.
     *
     * @return the comment list
     */
    public ArrayList<String> getCommentList() {
        return commentList;
    }

    /**
     * Sets comment list.
     *
     * @param commentList the comment list
     */
    public void setCommentList(ArrayList<String> commentList) {
        this.commentList = commentList;
    }


    /**
     * Add comment.
     *
     * @param postion     the postion
     * @param commentText the comment text
     */
    public void addComment(int postion, String commentText) {


        commentList.add(postion, commentText);
    }

}
