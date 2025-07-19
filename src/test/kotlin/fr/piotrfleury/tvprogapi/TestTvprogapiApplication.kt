package fr.piotrfleury.tvprogapi

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<TvprogapiApplication>().with(TestcontainersConfiguration::class).run(*args)
}
