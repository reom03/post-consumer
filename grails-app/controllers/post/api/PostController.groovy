package post.api

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class PostController {

    PostService postService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond postService.list(params), model:[postCount: postService.count()]
    }

    def show(Long id) {
        respond postService.get(id)
    }

    @Transactional
    def save(Post post) {
        if (post == null) {
            render status: NOT_FOUND
            return
        }
        if (post.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond post.errors
            return
        }

        try {
            postService.save(post)
        } catch (ValidationException e) {
            respond post.errors
            return
        }

        respond post, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Post post) {
        if (post == null) {
            render status: NOT_FOUND
            return
        }
        if (post.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond post.errors
            return
        }

        try {
            postService.save(post)
        } catch (ValidationException e) {
            respond post.errors
            return
        }

        respond post, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || postService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
