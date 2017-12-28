package com.lesforest;

import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringRpcApplication {

	@GRpcService
	public static class TestService extends TestServiceGrpc.TestServiceImplBase{



		@Override
		public void stubby(Msg request, StreamObserver<Msg> responseObserver) {
			System.out.println("STUBBY");
			Msg build = Msg.newBuilder()
					.setMsg("HI FROM SERVER")
					.build();
			responseObserver.onNext(build);
			responseObserver.onCompleted();

		}
//
		@Override
		public StreamObserver<Msg> streamy(StreamObserver<Msg> responseObserver) {
			System.out.println("streamy");


			Msg streamy = Msg.newBuilder().setMsg("STREAMY").build();


			for (int i = 0; i < 15; i++) {

				responseObserver.onNext(streamy);

			}


			return new StreamObserver<Msg>() {
				@Override
				public void onNext(Msg value) {

					System.out.println("TestService.onNext");
				}

				@Override
				public void onError(Throwable t) {

				}

				@Override
				public void onCompleted() {

				}
			};
		}
	}

	public static void main(String[] args) {

		SpringApplication.run(SpringRpcApplication.class, args);

	}
}
