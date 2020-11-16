class TestCode {
    private lateinit var mListener: ListenerBuilder

    fun setListener(listenerBuilder: ListenerBuilder.() -> Unit) {
        mListener = ListenerBuilder().also(listenerBuilder)
    }

    inner class ListenerBuilder {
        internal var mPlayAction: (() -> Unit)? = null
        internal var mPauseAction: (() -> Unit)? = null
        internal var mStopAction: (() -> Unit)? = null

        fun onPlay(action: () -> Unit) {
            mPlayAction = action
        }

        fun onPause(action: () -> Unit) {
            mPauseAction = action
        }

        fun onStop(action: () -> Unit) {
            mStopAction = action
        }
    }

}