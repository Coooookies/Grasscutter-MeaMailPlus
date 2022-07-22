package io.github.gmw.packetHandler;

import emu.grasscutter.game.player.Player;
import emu.grasscutter.net.packet.Opcodes;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.server.packet.recv.HandlerSetPlayerBornDataReq;
import io.github.gmw.utils.MailCore;
import io.github.gmw.utils.PlayerUtils;

@Opcodes(PacketOpcodes.SetPlayerBornDataReq)
public class PlayerBornHook extends HandlerSetPlayerBornDataReq {

    @Override
    public void handle(GameSession session, byte[] header, byte[] payload) throws Exception {
        super.handle(session, header, payload);
        int playerUid = session.getAccount().getReservedPlayerUid();

        // 第一次加入
        if(playerUid != 0) {
            MailCore.sendInitialMailToPlayer(playerUid);
            MailCore.sendDailySignInMailToPlayer(playerUid);
        }
    }
}
