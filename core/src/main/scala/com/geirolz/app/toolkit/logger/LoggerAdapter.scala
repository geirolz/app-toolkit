package com.geirolz.app.toolkit.logger

trait LoggerAdapter[LOGGER[_[_]]] {
  def toToolkit[F[_]](appLogger: LOGGER[F]): ToolkitLogger[F]
}
object LoggerAdapter {
  def apply[LOGGER[_[_]]: LoggerAdapter]: LoggerAdapter[LOGGER] = implicitly[LoggerAdapter[LOGGER]]

  implicit def id[L[K[_]] <: ToolkitLogger[K]]: LoggerAdapter[L] =
    new LoggerAdapter[L] {
      override def toToolkit[F[_]](underlying: L[F]): ToolkitLogger[F] = new ToolkitLogger[F] {
        override def info(message: => String): F[Unit]  = underlying.info(message)
        override def error(message: => String): F[Unit] = underlying.error(message)
        override def error(ex: Throwable)(message: => String): F[Unit] =
          underlying.error(ex)(message)
      }
    }
}
