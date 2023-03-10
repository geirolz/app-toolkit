package com.geirolz.app.toolkit.logger

import cats.effect.IO
import com.geirolz.app.toolkit.error.*
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.noop.NoOpLogger

class Log4CatsLoggerAdapterSuite extends munit.CatsEffectSuite {

  test("Implicit conversion with Logger") {
    val adapterLogger: LoggerAdapter[Logger] = implicitly[LoggerAdapter[Logger]]
    val tkLogger                             = adapterLogger.toToolkit(NoOpLogger[IO])

    assertIO_(
      tkLogger.info("msg") >> tkLogger.error(ex"BOOM!")("msg")
    )
  }
}
