package com.example.client.handler;

import com.example.core.MethodParams;
import com.example.entity.Person;
import com.example.service.PersonService;
import com.example.util.SerializerUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class MyClientHandler extends SimpleChannelInboundHandler {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 组装数据
        MethodParams methodParams = new MethodParams();
        methodParams.setServiceId(1);
        methodParams.setServiceClazz(PersonService.class);

        // 查询
//        methodParams.setMethodName("load");
//        methodParams.setParams(2);

        // 更新
        methodParams.setMethodName("update");
        methodParams.setParams(new Person(4, "李四", 30));

        byte[] protobuf = SerializerUtils.serializer(methodParams);
        ctx.writeAndFlush(Unpooled.copiedBuffer(protobuf));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 解析数据
        ByteBuf o = (ByteBuf) msg;
        byte[] protobuf = new byte[o.readableBytes()];
        o.readBytes(protobuf);

        Person person = SerializerUtils.deserializer(protobuf, Person.class);
        System.out.println(person);

        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
