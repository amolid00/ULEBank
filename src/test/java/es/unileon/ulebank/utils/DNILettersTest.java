package es.unileon.ulebank.utils;

import org.junit.Assert;
import org.junit.Test;

public class DNILettersTest {

    @Test
    public void tesDNI() {
        Assert.assertEquals(DniLetters.getInstance().isDniValid(6907700, 'H'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(91481910, 'P'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(99617625, 'W'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(81008615, 'Q'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(84424612, 'F'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(47838005, 'Y'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(29380649, 'N'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(89963984, 'J'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(49554503, 'Z'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(35925348, 'S'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(82648552, 'F'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(61320031, 'F'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(76441858, 'R'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(20765371, 'M'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(90444550, 'V'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(9789678, 'G'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(75360404, 'F'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(43773308, 'F'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(49251342, 'Q'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(6419148, 'D'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(13342143, 'G'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(79506018, 'D'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(58774021, 'M'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(9139728, 'B'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(59790203, 'R'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(50611466, 'N'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(83528034, 'S'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(27674537, 'V'),
                true);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(67327369, 'K'),
                true);
        Assert.assertNotSame(DniLetters.getInstance().isDniValid(12314, 'z'),
                true);
        Assert.assertNotSame(DniLetters.getInstance().isDniValid(1231234, 'z'),
                true);
        Assert.assertNotSame(DniLetters.getInstance()
                .isDniValid(112312314, 'z'), true);
        Assert.assertEquals(DniLetters.getInstance()
                .isDniValid(1000000000, 'A'), false);
        Assert.assertEquals(DniLetters.getInstance()
                .isDniValid(1000000000, 'C'), false);
        Assert.assertEquals(DniLetters.getInstance().isDniValid(10000000, 'Z'),
                true);
    }

}
