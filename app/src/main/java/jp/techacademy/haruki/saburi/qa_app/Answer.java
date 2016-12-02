package jp.techacademy.haruki.saburi.qa_app;

import java.io.Serializable;

/**
 * Created by tgaiacontentsdev on 2016/12/02.
 */

public class Answer implements Serializable {
    private String mbody;
    private String mName;
    private String mUid;
    private String mAnswerUid;

    public Answer(String body, String name, String uid, String answerUid){
        mbody = body;
        mName = name;
        mUid = uid;
        mAnswerUid = answerUid;
    }

    public String getBody(){
        return mbody;
    }

    public String getName(){
        return mName;
    }

    public String getUid(){
        return mUid;
    }

    public String getAnswerUid(){
        return mAnswerUid;
    }
}
