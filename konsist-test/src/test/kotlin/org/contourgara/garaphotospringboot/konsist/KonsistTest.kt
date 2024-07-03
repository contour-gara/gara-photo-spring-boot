package org.contourgara.garaphotospringboot.konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import com.lemonappdev.konsist.api.ext.list.withAnnotationOf
import com.lemonappdev.konsist.api.ext.list.withNameContaining
import com.lemonappdev.konsist.api.verify.assertTrue
import io.kotest.core.spec.style.WordSpec
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RestController

class KonsistTest : WordSpec({
    "アーキテクチャ" should {
        "オニオンアーキテクチャである" {
            Konsist
                .scopeFromProject("gara-photo-app-server")
                .assertArchitecture {
                    val presentation = Layer("presentation", "org.contourgara.garaphotospringboot.presentation..")
                    val application = Layer("application", "org.contourgara.garaphotospringboot.application..")
                    val domain = Layer("domain", "org.contourgara.garaphotospringboot.domain..")
                    val infrastructure = Layer("infrastructure", "org.contourgara.garaphotospringboot.infrastructure..")
                    val common = Layer("common", "org.contourgara.garaphotospringboot.common..")

                    presentation.dependsOn(application, common)
                    application.dependsOn(domain, common)
                    infrastructure.dependsOn(domain, common)
                    domain.dependsOnNothing()
                }
        }
    }

    "コントローラーアノテーションのついているクラス" should {
        val classes = Konsist
            .scopeFromModule("gara-photo-app-server")
            .classes()
            .withAnnotationOf(RestController::class)

        "presentation パッケージにある" {
            classes
                .assertTrue {
                    it.resideInPackage("..presentation")
                }
        }

        "クラス名が Controller で終わる" {
            classes
                .assertTrue {
                    it.name.endsWith("Controller")
                }
        }
    }

    "ユースケースパッケージにあるクラス" should {
        val scope = Konsist
            .scopeFromPackage(
                "org.contourgara.garaphotospringboot.application.usecase",
                moduleName = "gara-photo-app-server",
                sourceSetName = "main"
            )

        "クラス名が UseCase で終わる" {
            scope
                .classes()
                .assertTrue {
                    it.name.endsWith("UseCase")
                }
        }

        "Service アノテーションがついている" {
            scope
                .classes()
                .assertTrue {
                    it.hasAnnotationOf(Service::class)
                }
        }

        "テストが実装されている" {
            scope
                .classes()
                .assertTrue {
                    it.hasTestClasses()
                }
        }
    }

    "シナリオパッケージにあるクラス" should {
        val scope = Konsist
            .scopeFromPackage(
                "org.contourgara.garaphotospringboot.application.scenario",
                moduleName = "gara-photo-app-server",
                sourceSetName = "main"
            )

        "クラス名が Scenario で終わる" {
            scope
                .classes()
                .assertTrue {
                    it.name.endsWith("Scenario")
                }
        }

        "Service アノテーションがついている" {
            scope
                .classes()
                .assertTrue {
                    it.hasAnnotationOf(Service::class)
                }
        }

        "テストが実装されている" {
            scope
                .classes()
                .assertTrue {
                    it.hasTestClasses()
                }
        }
    }

    "リポジトリとついているクラス" When {
        val scope = Konsist
            .scopeFromProject(
                moduleName = "gara-photo-app-server",
                sourceSetName = "main"
            )

        "インターフェース" should {
            "ドメインパッケージにある" {
                scope
                    .interfaces()
                    .withNameContaining("Repository")
                    .assertTrue {
                        it.resideInPackage("..domain.infrastructure")
                    }
            }
        }

        "実装クラス" should {
            val classes = scope.classes().withNameContaining("Repository")

            "インフラストラクチャパッケージにある" {
                classes
                    .assertTrue {
                        it.resideInPackage("..infrastructure")
                    }
            }

            "名前に Impl が含まれる" {
                classes
                    .assertTrue {
                        it.hasNameContaining("Impl")
                    }
            }
        }
    }
})
