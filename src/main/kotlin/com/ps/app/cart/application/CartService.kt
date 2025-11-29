package com.ps.app.cart.application

import com.ps.app.cart.adapter.`in`.web.dto.CartDetailResponse
import com.ps.app.cart.adapter.out.persistence.CartDetailMapper
import com.ps.app.cart.application.port.out.CartDetailPort
import com.ps.app.cart.application.port.out.CartPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class CartService(
    private val cartPort: CartPort,
    private val cartDetailPort: CartDetailPort
) {
    fun getCartDetails(cartId: Long): List<CartDetailResponse> {
        return cartDetailPort.findByCartId(cartId)
            .map { CartDetailMapper.toResponse(it) }
    }

    @Transactional
    fun updateQuantity(cartDetailId: Long, quantity: Int): CartDetailResponse {
        val cartDetail = cartDetailPort.findCartDetailById(cartDetailId)
            ?: throw IllegalArgumentException("장바구니 상품을 찾을 수 없습니다")

        val updated = cartDetail.changeQuantity(quantity)
        val saved = cartDetailPort.save(updated)

        return CartDetailMapper.toResponse(saved)
    }
}
