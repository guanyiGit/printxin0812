package org.javatribe.calculator.domain.printer;

import lombok.Data;

public class TrackData {
    /**
     * data to be encoded on track 1.
     */
    private String track1Data;
    private String track2Data;
    private String track3Data;

    public String getTrack1Data() {
        return track1Data;
    }

    public void setTrack1Data(String track1Data) {
        this.track1Data = track1Data;
    }

    public String getTrack2Data() {
        return track2Data;
    }

    public void setTrack2Data(String track2Data) {
        this.track2Data = track2Data;
    }

    public String getTrack3Data() {
        return track3Data;
    }

    public void setTrack3Data(String track3Data) {
        this.track3Data = track3Data;
    }
}
