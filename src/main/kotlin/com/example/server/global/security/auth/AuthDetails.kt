package com.example.server.global.security.auth

import com.example.server.domain.user.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.Collections

class AuthDetails(
    private val user: User
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? = Collections.singletonList(
        SimpleGrantedAuthority("ROLE_" + user.userRole.name)
    )

    override fun getPassword(): String? = null

    override fun getUsername(): String = user.email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}