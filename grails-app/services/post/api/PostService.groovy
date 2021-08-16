package post.api

import grails.gorm.services.Service

@Service(Post)
interface PostService {

    Post get(Serializable id)

    List<Post> list(Map args)

    Long count()

    Post delete(Serializable id)

    Post save(Post post)

}
