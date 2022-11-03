package com.example.server.domain.officeGoingTime.presentation

import com.example.server.domain.officeGoingTime.service.RegisterOfficeGoingTimeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "출근", description = "출근 관련 API 입니다.")
@RequestMapping("/officegoing")
@RestController
class OfficeGoingTimeController(
    private val registerOfficeGoingTimeService: RegisterOfficeGoingTimeService
) {

    @Operation(summary = "출근 등록")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun registerOfficeGoingTime() {
        registerOfficeGoingTimeService.execute()
    }
}