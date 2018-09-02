package com.aliyil.gmtkjam18.entity.interfaces;

import com.aliyil.gmtkjam18.Note;

public interface NoteBase {
    float getLife();
    float getNormalizedLife();
    float hit();
    Note getNote();
}
