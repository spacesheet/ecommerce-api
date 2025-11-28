package com.ps.app.products.exception

sealed class ProductException(message: String) : RuntimeException(message)

class BookNotFoundException(message: String) : ProductException(message)
class PublisherNotFoundException(message: String) : ProductException(message)
class ProductNotFoundException(message: String) : ProductException(message)
class DuplicateBookException(message: String) : ProductException(message)
