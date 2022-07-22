package com.example.demo.exception

class DemoException(message: String, val errorCode: String) : RuntimeException(message)