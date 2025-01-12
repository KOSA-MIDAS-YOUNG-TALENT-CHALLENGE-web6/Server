package com.example.server.domain.user.presentation

import com.example.server.domain.user.presentation.dto.request.LoginRequest
import com.example.server.domain.user.presentation.dto.request.ModifyApplicationRequest
import com.example.server.domain.user.presentation.dto.request.ModifyPositionRequest
import com.example.server.domain.user.presentation.dto.request.SignupRequest
import com.example.server.domain.user.presentation.dto.response.ModifyNameRequest
import com.example.server.domain.user.presentation.dto.response.TodayWorkingTimeResponse
import com.example.server.domain.user.presentation.dto.response.UserElement
import com.example.server.domain.user.presentation.dto.response.UserListResponse
import com.example.server.domain.user.presentation.dto.response.UserResponse
import com.example.server.domain.user.presentation.dto.response.WeekWorkingTimeListResponse
import com.example.server.domain.user.service.LoginService
import com.example.server.domain.user.service.ModifyApplicationService
import com.example.server.domain.user.service.ModifyNameService
import com.example.server.domain.user.service.ModifyPositionService
import com.example.server.domain.user.service.QueryExpectationOfficeGoingTimeService
import com.example.server.domain.user.service.QueryExpectationQuittingTimeService
import com.example.server.domain.user.service.QueryTodayWorkingTimeService
import com.example.server.domain.user.service.QueryUserListService
import com.example.server.domain.user.service.QueryUserOfficeGoingService
import com.example.server.domain.user.service.QueryUserQuittingService
import com.example.server.domain.user.service.QueryUserService
import com.example.server.domain.user.service.QueryWeekWorkingTimeService
import com.example.server.domain.user.service.SignupService
import com.example.server.global.security.jwt.dto.TokenResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import javax.validation.Valid

@Tag(name = "유저", description = "유저 관련 API입니다.")
@RequestMapping("/user")
@RestController
class UserController(
    private val signupService: SignupService,
    private val loginService: LoginService,
    private val modifyApplicationService: ModifyApplicationService,
    private val modifyPositionService: ModifyPositionService,
    private val modifyNameService: ModifyNameService,
    private val queryTodayWorkingTimeService: QueryTodayWorkingTimeService,
    private val queryWeekWorkingTimeService: QueryWeekWorkingTimeService,
    private val queryUserOfficeGoingService: QueryUserOfficeGoingService,
    private val queryUserQuittingService: QueryUserQuittingService,
    private val queryUserService: QueryUserService,
    private val queryUserListResponse: QueryUserListService,
    private val queryExpectationQuittingTimeService: QueryExpectationQuittingTimeService,
    private val queryExpectationOfficeGoingTimeService: QueryExpectationOfficeGoingTimeService
) {

    @Operation(summary = "회원가입")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun signup(@Valid @RequestBody request: SignupRequest) {
        signupService.execute(request)
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): TokenResponse {
        return loginService.execute(request)
    }

    @Operation(summary = "소속 수정하기")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/application/{user-id}")
    fun modifyApplication(@PathVariable("user-id") userId: Int, @Valid @RequestBody request: ModifyApplicationRequest) {
        modifyApplicationService.execute(userId, request);
    }

    @Operation(summary = "직급 수정하기")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/position/{user-id}")
    fun modifyPosition(@PathVariable("user-id") userId: Int, @Valid @RequestBody request: ModifyPositionRequest) {
        modifyPositionService.execute(userId, request);
    }

    @Operation(summary = "이름 수정하기")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/name/{user-id}")
    fun modifyName(@PathVariable("user-id") userId: Int, @Valid @RequestBody request: ModifyNameRequest) {
        modifyNameService.execute(userId, request);
    }

    @Operation(summary = "오늘 총 근로 시간 조회")
    @GetMapping("/today")
    fun getTodayWorkingTime(): TodayWorkingTimeResponse {
        return queryTodayWorkingTimeService.execute();
    }

    @Operation(summary = "한 주 총 근로 시간 조회")
    @GetMapping("/week")
    fun getWeekWorkingTime(@RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) date: LocalDateTime): WeekWorkingTimeListResponse {
        return queryWeekWorkingTimeService.execute(date);
    }

    @Operation(summary = "출근한 유저 조회")
    @GetMapping("/officegoing")
    fun getOfficeGoing(): UserListResponse {
        return queryUserOfficeGoingService.execute();
    }

    @Operation(summary = "퇴근한 유저 조회")
    @GetMapping("/quitting")
    fun getQuitting(): UserListResponse {
        return queryUserQuittingService.execute();
    }

    @Operation(summary = "유저 조회")
    @GetMapping
    fun getUser(): UserElement {
        return queryUserService.execute();
    }

    @Operation(summary = "유저 전체 조회")
    @GetMapping("/every")
    fun getEveryUser(): UserResponse {
        return queryUserListResponse.execute();
    }

    @Operation(summary = "유저 예상 출근 시간 조회")
    @GetMapping("/expectationoffice/{user-id}")
    fun getExpectationOffice(@PathVariable("user-id") userId: Int): Int {
        return queryExpectationOfficeGoingTimeService.execute(userId);
    }

    @Operation(summary = "유저 예상 퇴근 시간 조회")
    @GetMapping("/expectationquitting/{user-id}")
    fun getExpectationQuitting(@PathVariable("user-id") userId: Int): Int {
        return queryExpectationQuittingTimeService.execute(userId);
    }
}