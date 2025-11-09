package pt.alticelabs.challenge.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigInteger;

@Schema(description = "Response containing the requested index and its computed Labseq value")
public record LabseqResponse(
        @Schema(description = "Requested index of the Labseq sequence")
        long index,

        @Schema(description = "Computed Labseq value corresponding to the given index")
        String value
) {
    public LabseqResponse(long index, BigInteger value) {
        this(index, value.toString());
    }
}
