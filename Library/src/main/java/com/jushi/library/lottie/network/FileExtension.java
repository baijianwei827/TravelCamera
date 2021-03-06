package com.jushi.library.lottie.network;


import com.jushi.library.lottie.lottie.L;

/**
 * Helpers for known Lottie file types.
 */
public enum FileExtension {
  JSON(".json"),
  ZIP(".zip");

  public final String extension;

  FileExtension(String extension) {
    this.extension = extension;
  }

  public String tempExtension() {
    return ".temp" + extension;
  }

  @Override public String toString() {
    return extension;
  }

  public static FileExtension forFile(String filename) {
    for (FileExtension e : values()) {
      if (filename.endsWith(e.extension)) {
        return e;
      }
    }
    // Default to Json.
    L.warn("Unable to find correct extension for " + filename);
    return JSON;
  }
}
