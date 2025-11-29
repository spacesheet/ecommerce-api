package com.ps.app.orders.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OrderDetailJpaRepository : JpaRepository<OrderDetailEntity, Long> {

    // 주문 ID로 주문 상세 목록 조회
    fun findByOrderId(orderId: Long): List<OrderDetailEntity>

    // 주문 ID로 주문 상세 목록 조회 (Product와 함께 fetch join)
    @Query("""
        SELECT od FROM OrderDetailEntity od
        JOIN FETCH od.product p
        JOIN FETCH p.category
        LEFT JOIN FETCH p.productTags pt
        LEFT JOIN FETCH pt.tag
        WHERE od.order.id = :orderId
    """)
    fun findByOrderIdWithProduct(@Param("orderId") orderId: Long): List<OrderDetailEntity>

    // 사용자 ID로 주문 상세 목록 조회
    @Query("""
        SELECT od FROM OrderDetailEntity od
        JOIN FETCH od.order o
        JOIN FETCH od.product p
        WHERE o.user.id = :userId
        ORDER BY od.createAt DESC
    """)
    fun findByUserIdWithDetails(@Param("userId") userId: Long): List<OrderDetailEntity>

    // 상품 ID로 주문 상세 목록 조회
    fun findByProductId(productId: Long): List<OrderDetailEntity>

    // 상품 ID로 주문 상세 목록 조회 (주문 정보와 함께)
    @Query("""
        SELECT od FROM OrderDetailEntity od
        JOIN FETCH od.order o
        JOIN FETCH o.user
        WHERE od.product.id = :productId
        ORDER BY od.createAt DESC
    """)
    fun findByProductIdWithOrder(@Param("productId") productId: Long): List<OrderDetailEntity>

    // 특정 상태의 주문 상세 목록 조회
    @Query("""
        SELECT od FROM OrderDetailEntity od
        JOIN FETCH od.product p
        WHERE od.orderStatus = :status
        ORDER BY od.createAt DESC
    """)
    fun findByStatus(@Param("status") status: String): List<OrderDetailEntity>

    // 사용자별 특정 상태의 주문 상세 조회
    @Query("""
        SELECT od FROM OrderDetailEntity od
        JOIN FETCH od.order o
        JOIN FETCH od.product p
        WHERE o.user.id = :userId AND od.orderStatus = :status
        ORDER BY od.createAt DESC
    """)
    fun findByUserIdAndStatus(
        @Param("userId") userId: Long,
        @Param("status") status: String
    ): List<OrderDetailEntity>

    // 주문 ID와 상품 ID로 주문 상세 조회
    @Query("""
        SELECT od FROM OrderDetailEntity od
        WHERE od.order.id = :orderId AND od.product.id = :productId
    """)
    fun findByOrderIdAndProductId(
        @Param("orderId") orderId: Long,
        @Param("productId") productId: Long
    ): OrderDetailEntity?

    // 특정 기간 동안의 주문 상세 조회
    @Query("""
        SELECT od FROM OrderDetailEntity od
        JOIN FETCH od.product p
        WHERE od.createAt BETWEEN :startDate AND :endDate
        ORDER BY od.createAt DESC
    """)
    fun findByCreatedAtBetween(
        @Param("startDate") startDate: java.time.LocalDateTime,
        @Param("endDate") endDate: java.time.LocalDateTime
    ): List<OrderDetailEntity>

    // 리뷰 작성 가능한 주문 상세 조회 (배송 완료 + 리뷰 없음)
    @Query("""
        SELECT od FROM OrderDetailEntity od
        JOIN FETCH od.product p
        LEFT JOIN ReviewEntity r ON r.orderDetail.id = od.id
        WHERE od.order.user.id = :userId 
        AND od.orderStatus = 'DELIVERED'
        AND r.id IS NULL
        ORDER BY od.updateAt DESC
    """)
    fun findReviewableOrderDetails(@Param("userId") userId: Long): List<OrderDetailEntity>

    // 주문별 총 금액 계산
    @Query("""
        SELECT SUM((od.price * od.quantity) + COALESCE(w.price, 0))
        FROM OrderDetailEntity od
        LEFT JOIN od.wrapping w
        WHERE od.order.id = :orderId
    """)
    fun calculateOrderTotal(@Param("orderId") orderId: Long): Int?

    // 상품별 판매 수량 집계
    @Query("""
        SELECT SUM(od.quantity)
        FROM OrderDetailEntity od
        WHERE od.product.id = :productId
        AND od.orderStatus NOT IN ('CANCELLED', 'REFUNDED', 'RETURNED')
    """)
    fun getTotalSoldQuantity(@Param("productId") productId: Long): Long?

    // 포장 옵션 사용 여부로 조회
    fun findByWrapTrue(): List<OrderDetailEntity>

    // 특정 포장 옵션으로 조회
    fun findByWrappingId(wrappingId: Long): List<OrderDetailEntity>

    // 주문 상세 존재 여부 확인
    fun existsByOrderIdAndProductId(orderId: Long, productId: Long): Boolean

    // 특정 상태의 주문 상세 개수
    fun countByStatus(status: String): Long

    // 사용자별 주문 상세 개수
    @Query("""
        SELECT COUNT(od)
        FROM OrderDetailEntity od
        WHERE od.order.user.id = :userId
    """)
    fun countByUserId(@Param("userId") userId: Long): Long

    // 배송 완료된 주문 상세 조회 (최근 30일)
    @Query("""
        SELECT od FROM OrderDetailEntity od
        JOIN FETCH od.product p
        WHERE od.orderStatus = 'DELIVERED'
        AND od.updateAt >= :thirtyDaysAgo
        ORDER BY od.updateAt DESC
    """)
    fun findRecentlyDelivered(
        @Param("thirtyDaysAgo") thirtyDaysAgo: java.time.LocalDateTime
    ): List<OrderDetailEntity>

    // 취소 가능한 주문 상세 조회
    @Query("""
        SELECT od FROM OrderDetailEntity od
        JOIN FETCH od.product p
        WHERE od.order.id = :orderId
        AND od.orderStatus IN ('PENDING', 'CONFIRMED', 'PROCESSING', 'SHIPPING')
    """)
    fun findCancellableByOrderId(@Param("orderId") orderId: Long): List<OrderDetailEntity>

    // 반품 가능한 주문 상세 조회
    @Query("""
        SELECT od FROM OrderDetailEntity od
        JOIN FETCH od.product p
        WHERE od.order.user.id = :userId
        AND od.orderStatus IN ('SHIPPED', 'DELIVERED')
        ORDER BY od.updateAt DESC
    """)
    fun findReturnableByUserId(@Param("userId") userId: Long): List<OrderDetailEntity>
}
