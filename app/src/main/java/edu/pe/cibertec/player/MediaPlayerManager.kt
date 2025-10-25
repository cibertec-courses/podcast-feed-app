package edu.pe.cibertec.player

import android.media.MediaPlayer

class MediaPlayerManager {
    private var mediaPlayer: MediaPlayer? = null
    private var currentUrl : String? = null

    fun play(url: String){
        if(currentUrl == url && mediaPlayer != null){
            mediaPlayer?.start()
        }else{
            stop()
            mediaPlayer = mediaPlayer?.apply{
                setDataSource(url)
                prepareAsync()
                setOnPreparedListener {
                    start()
                }
            }
            currentUrl=url
        }
    }
    fun pause(){
        mediaPlayer?.pause()
    }
    fun stop(){
        mediaPlayer?.apply{
            stop()
            relese()
        }
        mediaPlayer=null
        currentUrl=null
    }

    // funciones de verificaciones
    fun isPlaying(): Boolean{
        return  mediaPlayer?.isPlaying ?: false
    }
    fun relese(){
        stop()
    }


}