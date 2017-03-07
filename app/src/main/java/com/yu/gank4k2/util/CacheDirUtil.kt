package com.yu.gank4k2.util

import android.content.Context
import android.os.Environment

import java.io.File

/**
 * 缓存文件夹工具包
 * Created by yu on 2016/12/22.
 */
object CacheDirUtil {
    /**
     * 返回缓存文件夹
     */
    fun getCacheFile(context: Context): File {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            var file = context.externalCacheDir//获取系统管理的sd卡缓存文件
            if (file == null) {//如果获取的为空,就是用自己定义的缓存文件夹做缓存路径
                file = File(getCacheFilePath(context))
                if (!file.exists()) {
                    file.mkdirs()
                }
            }
            return file
        } else {
            return context.cacheDir
        }
    }

    /**
     * 获取自定义缓存文件地址
     */
    private fun getCacheFilePath(context: Context): String {
        val packageName = context.packageName
        return "/mnt/sdcard/" + packageName
    }

    /**
     * 使用递归获取目录文件大小
     */
    fun getDirSize(dir: File?): Long {
        if (dir == null) {
            return 0
        }
        if (!dir.isDirectory) {
            return 0
        }
        var dirSize: Long = 0
        val files = dir.listFiles()
        for (file in files) {
            if (file.isFile) {
                dirSize += file.length()
            } else if (file.isDirectory) {
                dirSize += file.length()
                dirSize += getDirSize(file) // 递归调用继续统计
            }
        }
        return dirSize
    }

    /**
     * 使用递归删除文件夹
     */
    fun deleteDir(dir: File?): Boolean {
        if (dir == null) {
            return false
        }
        if (!dir.isDirectory) {
            return false
        }
        val files = dir.listFiles()
        for (file in files) {
            if (file.isFile) {
                file.delete()
            } else if (file.isDirectory) {
                deleteDir(file) // 递归调用继续删除
            }
        }
        return true
    }
}
