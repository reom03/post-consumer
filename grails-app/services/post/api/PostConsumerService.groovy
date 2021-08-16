package post.api

import grails.converters.JSON
import grails.gorm.transactions.Transactional
import grails.plugin.jms.Queue

@Transactional
class PostConsumerService {

    PostService postService

    static expose = ["jms"]
    @Queue(name = 'ellucian:message')
    def handleMessage(String msg){
        def jsonMessage = JSON.parse(msg)
        Post postInstance = Post.createFromJSON(jsonMessage)
        postService.save(postInstance)
        null
    }
}
