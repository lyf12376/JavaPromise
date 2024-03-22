package pub.telephone.javapromise.async.promise

class PromiseState<S>(
        @Suppress("PropertyName")
        @JvmField
        val CancelledBroadcast: PromiseCancelledBroadcast,
        @JvmField
        internal val self: Promise<S>
)
