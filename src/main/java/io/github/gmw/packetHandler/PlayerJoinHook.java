package io.github.gmw.packetHandler;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.server.packet.recv.HandlerPlayerLoginReq;
import emu.grasscutter.server.game.GameSession;
import io.github.gmw.utils.MailCore;
import io.github.gmw.utils.PlayerUtils;

@Opcodes(PacketOpcodes.PlayerLoginReq)
public class PlayerJoinHook extends HandlerPlayerLoginReq {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        super.handle(session, header, payload);
        Player player = session.getPlayer();

        if (player == null) return; // new player
        if(PlayerUtils.isFirstLoginToday(player)) { // first login today
            if (PlayerUtils.isPlayerBirthDay(player)) { // is player birthday
                MailCore.sendBirthDayMailToPlayer(player);
            }

            // daily mail
            MailCore.sendDailySignInMailToPlayer(player);
        }
    }
}
