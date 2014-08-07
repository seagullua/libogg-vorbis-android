package com.ideaheap.io;

import java.io.IOException;

/**
 * A native wrapper to the xiph.org vorbis file Input Stream. 
 * @author nwertzberger
 *
 */
public class VorbisFileInputStream extends AudioInputStream {
	private static Object _lock = new Object();
	private final VorbisInfo info;
	
	public VorbisInfo getInfo() {
		return info;
	}

	private final int oggStreamIdx;

	
	/**
	 * Opens a file for reading and parses any comments out of the file header.
	 */
	public VorbisFileInputStream(String fname) throws IOException {
		synchronized(_lock)
		{
			info = new VorbisInfo();
			oggStreamIdx = this.create(fname, info);
		}
	}
	
	@Override
	public void close() throws IOException {
		synchronized(_lock)
		{
			this.closeStreamIdx(oggStreamIdx);
		}
	}
	
	/**
	 * Returns interleaved PCM data from the vorbis stream.
	 * @param pcmBuffer
	 * @param offset
	 * @param length
	 * @return
	 * @throws IOException
	 */
	public int read(short[] pcmBuffer, int offset, int length) throws IOException {
		synchronized(_lock)
		{
			return this.readStreamIdx(oggStreamIdx, pcmBuffer, offset, length);
		}
	}
	
	private native int create(String fname, VorbisInfo info) throws IOException;
	private native void closeStreamIdx(int sidx) throws IOException;
	
	/**
	 * This just returns all the channels interleaved together. I assume this
	 * is how android wants it.
	 * @param pcm
	 * @return
	 * @throws IOException
	 */
	private native int readStreamIdx(int sidx, short [] pcm, int offset, int size) throws IOException;
	
	/**
	 * Skips over the number of samples specified.  This skip doesn't
	 * account for channels.
	 * 
	 * @param samples
	 * @return
	 * @throws IOException
	 */
	private native long skipStreamIdx(int sidx, long samples) throws IOException;

}
