LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := vorbis_stream_static
LOCAL_MODULE_FILENAME := vorbis-stream
LOCAL_CFLAGS += -I$(LOCAL_PATH)/../include -fsigned-char
LOCAL_CFLAGS += -march=armv6 -marm -mfloat-abi=softfp -mfpu=vfp


LOCAL_SRC_FILES := \
	vorbis-fileoutputstream.c \
	vorbis-fileinputstream.c \
	jni-util.c

include $(BUILD_STATIC_LIBRARY)
