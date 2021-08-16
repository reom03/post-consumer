package post.api

import org.grails.web.json.JSONElement

class Post {

    Long userId
    String title
    Date createdOn = new Date()

    static constraints = {
    }

    static Post createFromJSON(JSONElement postJson){
        Post instance = Post.newInstance()
        instance.with {
            userId = postJson.userId
            title = postJson.title
        }
        return instance
    }
}
