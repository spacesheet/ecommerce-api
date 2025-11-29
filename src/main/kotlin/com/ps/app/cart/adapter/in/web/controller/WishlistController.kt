package com.ps.app.cart.adapter.`in`.web.controller

import com.ps.app.cart.adapter.`in`.web.dto.AddWishlistRequest
import com.ps.app.cart.adapter.`in`.web.dto.WishlistResponse
import com.ps.app.cart.application.service.WishlistService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/wishlists")
class WishlistController(
    private val wishlistService: WishlistService
) {

    @PostMapping
    fun addToWishlist(
        @RequestBody request: AddWishlistRequest
    ): ResponseEntity<WishlistResponse> {
        val response = wishlistService.addToWishlist(request.userId, request.productId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/users/{userId}")
    fun getWishlistByUser(
        @PathVariable userId: Long
    ): ResponseEntity<List<WishlistResponse>> {
        val wishlists = wishlistService.getWishlistByUser(userId)
        return ResponseEntity.ok(wishlists)
    }

    @DeleteMapping("/{wishlistId}")
    fun removeFromWishlist(
        @PathVariable wishlistId: Long
    ): ResponseEntity<Void> {
        wishlistService.removeFromWishlist(wishlistId)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/users/{userId}/products/{productId}")
    fun removeProductFromWishlist(
        @PathVariable userId: Long,
        @PathVariable productId: Long
    ): ResponseEntity<Void> {
        wishlistService.removeProductFromWishlist(userId, productId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/users/{userId}/products/{productId}/exists")
    fun checkInWishlist(
        @PathVariable userId: Long,
        @PathVariable productId: Long
    ): ResponseEntity<Boolean> {
        val exists = wishlistService.isInWishlist(userId, productId)
        return ResponseEntity.ok(exists)
    }
}
