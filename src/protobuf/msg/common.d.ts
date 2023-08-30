import * as $protobuf from "protobufjs";
import Long = require("long");
/** Namespace common. */
export namespace common {

    /** CommonDef enum. */
    enum CommonDef {
        _Dummy = 0,
        _PingRequest = 1,
        _PingResponse = 2,
        _Response = 3,
        _KickOutUserResponse = 4,
        _CheckInTicketRequest = 11,
        _CheckInTicketResponse = 12,
        _ReconnRequest = 13,
        _ReconnResponse = 14
    }

    /** Properties of a PingRequest. */
    interface IPingRequest {

        /** PingRequest pingId */
        pingId?: (number|null);
    }

    /** Represents a PingRequest. */
    class PingRequest implements IPingRequest {

        /**
         * Constructs a new PingRequest.
         * @param [properties] Properties to set
         */
        constructor(properties?: common.IPingRequest);

        /** PingRequest pingId. */
        public pingId: number;

        /**
         * Creates a new PingRequest instance using the specified properties.
         * @param [properties] Properties to set
         * @returns PingRequest instance
         */
        public static create(properties?: common.IPingRequest): common.PingRequest;

        /**
         * Encodes the specified PingRequest message. Does not implicitly {@link common.PingRequest.verify|verify} messages.
         * @param message PingRequest message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encode(message: common.IPingRequest, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Encodes the specified PingRequest message, length delimited. Does not implicitly {@link common.PingRequest.verify|verify} messages.
         * @param message PingRequest message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encodeDelimited(message: common.IPingRequest, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Decodes a PingRequest message from the specified reader or buffer.
         * @param reader Reader or buffer to decode from
         * @param [length] Message length if known beforehand
         * @returns PingRequest
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): common.PingRequest;

        /**
         * Decodes a PingRequest message from the specified reader or buffer, length delimited.
         * @param reader Reader or buffer to decode from
         * @returns PingRequest
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): common.PingRequest;

        /**
         * Verifies a PingRequest message.
         * @param message Plain object to verify
         * @returns `null` if valid, otherwise the reason why it is not
         */
        public static verify(message: { [k: string]: any }): (string|null);

        /**
         * Creates a PingRequest message from a plain object. Also converts values to their respective internal types.
         * @param object Plain object
         * @returns PingRequest
         */
        public static fromObject(object: { [k: string]: any }): common.PingRequest;

        /**
         * Creates a plain object from a PingRequest message. Also converts values to other types if specified.
         * @param message PingRequest
         * @param [options] Conversion options
         * @returns Plain object
         */
        public static toObject(message: common.PingRequest, options?: $protobuf.IConversionOptions): { [k: string]: any };

        /**
         * Converts this PingRequest to JSON.
         * @returns JSON object
         */
        public toJSON(): { [k: string]: any };

        /**
         * Gets the default type url for PingRequest
         * @param [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
         * @returns The default type url
         */
        public static getTypeUrl(typeUrlPrefix?: string): string;
    }

    /** Properties of a PingResponse. */
    interface IPingResponse {

        /** PingResponse pingId */
        pingId?: (number|null);
    }

    /** Represents a PingResponse. */
    class PingResponse implements IPingResponse {

        /**
         * Constructs a new PingResponse.
         * @param [properties] Properties to set
         */
        constructor(properties?: common.IPingResponse);

        /** PingResponse pingId. */
        public pingId: number;

        /**
         * Creates a new PingResponse instance using the specified properties.
         * @param [properties] Properties to set
         * @returns PingResponse instance
         */
        public static create(properties?: common.IPingResponse): common.PingResponse;

        /**
         * Encodes the specified PingResponse message. Does not implicitly {@link common.PingResponse.verify|verify} messages.
         * @param message PingResponse message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encode(message: common.IPingResponse, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Encodes the specified PingResponse message, length delimited. Does not implicitly {@link common.PingResponse.verify|verify} messages.
         * @param message PingResponse message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encodeDelimited(message: common.IPingResponse, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Decodes a PingResponse message from the specified reader or buffer.
         * @param reader Reader or buffer to decode from
         * @param [length] Message length if known beforehand
         * @returns PingResponse
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): common.PingResponse;

        /**
         * Decodes a PingResponse message from the specified reader or buffer, length delimited.
         * @param reader Reader or buffer to decode from
         * @returns PingResponse
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): common.PingResponse;

        /**
         * Verifies a PingResponse message.
         * @param message Plain object to verify
         * @returns `null` if valid, otherwise the reason why it is not
         */
        public static verify(message: { [k: string]: any }): (string|null);

        /**
         * Creates a PingResponse message from a plain object. Also converts values to their respective internal types.
         * @param object Plain object
         * @returns PingResponse
         */
        public static fromObject(object: { [k: string]: any }): common.PingResponse;

        /**
         * Creates a plain object from a PingResponse message. Also converts values to other types if specified.
         * @param message PingResponse
         * @param [options] Conversion options
         * @returns Plain object
         */
        public static toObject(message: common.PingResponse, options?: $protobuf.IConversionOptions): { [k: string]: any };

        /**
         * Converts this PingResponse to JSON.
         * @returns JSON object
         */
        public toJSON(): { [k: string]: any };

        /**
         * Gets the default type url for PingResponse
         * @param [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
         * @returns The default type url
         */
        public static getTypeUrl(typeUrlPrefix?: string): string;
    }

    /** Properties of a CheckInTicketRequest. */
    interface ICheckInTicketRequest {

        /** CheckInTicketRequest userId */
        userId?: (number|Long|null);

        /** CheckInTicketRequest ticket */
        ticket?: (string|null);
    }

    /** Represents a CheckInTicketRequest. */
    class CheckInTicketRequest implements ICheckInTicketRequest {

        /**
         * Constructs a new CheckInTicketRequest.
         * @param [properties] Properties to set
         */
        constructor(properties?: common.ICheckInTicketRequest);

        /** CheckInTicketRequest userId. */
        public userId: (number|Long);

        /** CheckInTicketRequest ticket. */
        public ticket: string;

        /**
         * Creates a new CheckInTicketRequest instance using the specified properties.
         * @param [properties] Properties to set
         * @returns CheckInTicketRequest instance
         */
        public static create(properties?: common.ICheckInTicketRequest): common.CheckInTicketRequest;

        /**
         * Encodes the specified CheckInTicketRequest message. Does not implicitly {@link common.CheckInTicketRequest.verify|verify} messages.
         * @param message CheckInTicketRequest message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encode(message: common.ICheckInTicketRequest, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Encodes the specified CheckInTicketRequest message, length delimited. Does not implicitly {@link common.CheckInTicketRequest.verify|verify} messages.
         * @param message CheckInTicketRequest message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encodeDelimited(message: common.ICheckInTicketRequest, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Decodes a CheckInTicketRequest message from the specified reader or buffer.
         * @param reader Reader or buffer to decode from
         * @param [length] Message length if known beforehand
         * @returns CheckInTicketRequest
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): common.CheckInTicketRequest;

        /**
         * Decodes a CheckInTicketRequest message from the specified reader or buffer, length delimited.
         * @param reader Reader or buffer to decode from
         * @returns CheckInTicketRequest
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): common.CheckInTicketRequest;

        /**
         * Verifies a CheckInTicketRequest message.
         * @param message Plain object to verify
         * @returns `null` if valid, otherwise the reason why it is not
         */
        public static verify(message: { [k: string]: any }): (string|null);

        /**
         * Creates a CheckInTicketRequest message from a plain object. Also converts values to their respective internal types.
         * @param object Plain object
         * @returns CheckInTicketRequest
         */
        public static fromObject(object: { [k: string]: any }): common.CheckInTicketRequest;

        /**
         * Creates a plain object from a CheckInTicketRequest message. Also converts values to other types if specified.
         * @param message CheckInTicketRequest
         * @param [options] Conversion options
         * @returns Plain object
         */
        public static toObject(message: common.CheckInTicketRequest, options?: $protobuf.IConversionOptions): { [k: string]: any };

        /**
         * Converts this CheckInTicketRequest to JSON.
         * @returns JSON object
         */
        public toJSON(): { [k: string]: any };

        /**
         * Gets the default type url for CheckInTicketRequest
         * @param [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
         * @returns The default type url
         */
        public static getTypeUrl(typeUrlPrefix?: string): string;
    }

    /** Properties of a CheckInTicketResponse. */
    interface ICheckInTicketResponse {

        /** CheckInTicketResponse userId */
        userId?: (number|Long|null);

        /** CheckInTicketResponse succezz */
        succezz?: (boolean|null);
    }

    /** Represents a CheckInTicketResponse. */
    class CheckInTicketResponse implements ICheckInTicketResponse {

        /**
         * Constructs a new CheckInTicketResponse.
         * @param [properties] Properties to set
         */
        constructor(properties?: common.ICheckInTicketResponse);

        /** CheckInTicketResponse userId. */
        public userId: (number|Long);

        /** CheckInTicketResponse succezz. */
        public succezz: boolean;

        /**
         * Creates a new CheckInTicketResponse instance using the specified properties.
         * @param [properties] Properties to set
         * @returns CheckInTicketResponse instance
         */
        public static create(properties?: common.ICheckInTicketResponse): common.CheckInTicketResponse;

        /**
         * Encodes the specified CheckInTicketResponse message. Does not implicitly {@link common.CheckInTicketResponse.verify|verify} messages.
         * @param message CheckInTicketResponse message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encode(message: common.ICheckInTicketResponse, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Encodes the specified CheckInTicketResponse message, length delimited. Does not implicitly {@link common.CheckInTicketResponse.verify|verify} messages.
         * @param message CheckInTicketResponse message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encodeDelimited(message: common.ICheckInTicketResponse, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Decodes a CheckInTicketResponse message from the specified reader or buffer.
         * @param reader Reader or buffer to decode from
         * @param [length] Message length if known beforehand
         * @returns CheckInTicketResponse
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): common.CheckInTicketResponse;

        /**
         * Decodes a CheckInTicketResponse message from the specified reader or buffer, length delimited.
         * @param reader Reader or buffer to decode from
         * @returns CheckInTicketResponse
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): common.CheckInTicketResponse;

        /**
         * Verifies a CheckInTicketResponse message.
         * @param message Plain object to verify
         * @returns `null` if valid, otherwise the reason why it is not
         */
        public static verify(message: { [k: string]: any }): (string|null);

        /**
         * Creates a CheckInTicketResponse message from a plain object. Also converts values to their respective internal types.
         * @param object Plain object
         * @returns CheckInTicketResponse
         */
        public static fromObject(object: { [k: string]: any }): common.CheckInTicketResponse;

        /**
         * Creates a plain object from a CheckInTicketResponse message. Also converts values to other types if specified.
         * @param message CheckInTicketResponse
         * @param [options] Conversion options
         * @returns Plain object
         */
        public static toObject(message: common.CheckInTicketResponse, options?: $protobuf.IConversionOptions): { [k: string]: any };

        /**
         * Converts this CheckInTicketResponse to JSON.
         * @returns JSON object
         */
        public toJSON(): { [k: string]: any };

        /**
         * Gets the default type url for CheckInTicketResponse
         * @param [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
         * @returns The default type url
         */
        public static getTypeUrl(typeUrlPrefix?: string): string;
    }

    /** Properties of a ReconnRequest. */
    interface IReconnRequest {

        /** ReconnRequest userId */
        userId?: (number|Long|null);

        /** ReconnRequest ukey */
        ukey?: (string|null);

        /** ReconnRequest ukeyExpireAt */
        ukeyExpireAt?: (number|Long|null);
    }

    /** Represents a ReconnRequest. */
    class ReconnRequest implements IReconnRequest {

        /**
         * Constructs a new ReconnRequest.
         * @param [properties] Properties to set
         */
        constructor(properties?: common.IReconnRequest);

        /** ReconnRequest userId. */
        public userId: (number|Long);

        /** ReconnRequest ukey. */
        public ukey: string;

        /** ReconnRequest ukeyExpireAt. */
        public ukeyExpireAt: (number|Long);

        /**
         * Creates a new ReconnRequest instance using the specified properties.
         * @param [properties] Properties to set
         * @returns ReconnRequest instance
         */
        public static create(properties?: common.IReconnRequest): common.ReconnRequest;

        /**
         * Encodes the specified ReconnRequest message. Does not implicitly {@link common.ReconnRequest.verify|verify} messages.
         * @param message ReconnRequest message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encode(message: common.IReconnRequest, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Encodes the specified ReconnRequest message, length delimited. Does not implicitly {@link common.ReconnRequest.verify|verify} messages.
         * @param message ReconnRequest message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encodeDelimited(message: common.IReconnRequest, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Decodes a ReconnRequest message from the specified reader or buffer.
         * @param reader Reader or buffer to decode from
         * @param [length] Message length if known beforehand
         * @returns ReconnRequest
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): common.ReconnRequest;

        /**
         * Decodes a ReconnRequest message from the specified reader or buffer, length delimited.
         * @param reader Reader or buffer to decode from
         * @returns ReconnRequest
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): common.ReconnRequest;

        /**
         * Verifies a ReconnRequest message.
         * @param message Plain object to verify
         * @returns `null` if valid, otherwise the reason why it is not
         */
        public static verify(message: { [k: string]: any }): (string|null);

        /**
         * Creates a ReconnRequest message from a plain object. Also converts values to their respective internal types.
         * @param object Plain object
         * @returns ReconnRequest
         */
        public static fromObject(object: { [k: string]: any }): common.ReconnRequest;

        /**
         * Creates a plain object from a ReconnRequest message. Also converts values to other types if specified.
         * @param message ReconnRequest
         * @param [options] Conversion options
         * @returns Plain object
         */
        public static toObject(message: common.ReconnRequest, options?: $protobuf.IConversionOptions): { [k: string]: any };

        /**
         * Converts this ReconnRequest to JSON.
         * @returns JSON object
         */
        public toJSON(): { [k: string]: any };

        /**
         * Gets the default type url for ReconnRequest
         * @param [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
         * @returns The default type url
         */
        public static getTypeUrl(typeUrlPrefix?: string): string;
    }

    /** Properties of a ReconnResponse. */
    interface IReconnResponse {

        /** ReconnResponse userId */
        userId?: (number|Long|null);

        /** ReconnResponse ukey */
        ukey?: (string|null);

        /** ReconnResponse ukeyExpire */
        ukeyExpire?: (number|Long|null);

        /** ReconnResponse ok */
        ok?: (boolean|null);
    }

    /** Represents a ReconnResponse. */
    class ReconnResponse implements IReconnResponse {

        /**
         * Constructs a new ReconnResponse.
         * @param [properties] Properties to set
         */
        constructor(properties?: common.IReconnResponse);

        /** ReconnResponse userId. */
        public userId: (number|Long);

        /** ReconnResponse ukey. */
        public ukey: string;

        /** ReconnResponse ukeyExpire. */
        public ukeyExpire: (number|Long);

        /** ReconnResponse ok. */
        public ok: boolean;

        /**
         * Creates a new ReconnResponse instance using the specified properties.
         * @param [properties] Properties to set
         * @returns ReconnResponse instance
         */
        public static create(properties?: common.IReconnResponse): common.ReconnResponse;

        /**
         * Encodes the specified ReconnResponse message. Does not implicitly {@link common.ReconnResponse.verify|verify} messages.
         * @param message ReconnResponse message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encode(message: common.IReconnResponse, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Encodes the specified ReconnResponse message, length delimited. Does not implicitly {@link common.ReconnResponse.verify|verify} messages.
         * @param message ReconnResponse message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encodeDelimited(message: common.IReconnResponse, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Decodes a ReconnResponse message from the specified reader or buffer.
         * @param reader Reader or buffer to decode from
         * @param [length] Message length if known beforehand
         * @returns ReconnResponse
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): common.ReconnResponse;

        /**
         * Decodes a ReconnResponse message from the specified reader or buffer, length delimited.
         * @param reader Reader or buffer to decode from
         * @returns ReconnResponse
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): common.ReconnResponse;

        /**
         * Verifies a ReconnResponse message.
         * @param message Plain object to verify
         * @returns `null` if valid, otherwise the reason why it is not
         */
        public static verify(message: { [k: string]: any }): (string|null);

        /**
         * Creates a ReconnResponse message from a plain object. Also converts values to their respective internal types.
         * @param object Plain object
         * @returns ReconnResponse
         */
        public static fromObject(object: { [k: string]: any }): common.ReconnResponse;

        /**
         * Creates a plain object from a ReconnResponse message. Also converts values to other types if specified.
         * @param message ReconnResponse
         * @param [options] Conversion options
         * @returns Plain object
         */
        public static toObject(message: common.ReconnResponse, options?: $protobuf.IConversionOptions): { [k: string]: any };

        /**
         * Converts this ReconnResponse to JSON.
         * @returns JSON object
         */
        public toJSON(): { [k: string]: any };

        /**
         * Gets the default type url for ReconnResponse
         * @param [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
         * @returns The default type url
         */
        public static getTypeUrl(typeUrlPrefix?: string): string;
    }

    /** Properties of a Response. */
    interface IResponse {

        /** Response code */
        code?: (number|null);

        /** Response msg */
        msg?: (string|null);
    }

    /** Represents a Response. */
    class Response implements IResponse {

        /**
         * Constructs a new Response.
         * @param [properties] Properties to set
         */
        constructor(properties?: common.IResponse);

        /** Response code. */
        public code: number;

        /** Response msg. */
        public msg: string;

        /**
         * Creates a new Response instance using the specified properties.
         * @param [properties] Properties to set
         * @returns Response instance
         */
        public static create(properties?: common.IResponse): common.Response;

        /**
         * Encodes the specified Response message. Does not implicitly {@link common.Response.verify|verify} messages.
         * @param message Response message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encode(message: common.IResponse, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Encodes the specified Response message, length delimited. Does not implicitly {@link common.Response.verify|verify} messages.
         * @param message Response message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encodeDelimited(message: common.IResponse, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Decodes a Response message from the specified reader or buffer.
         * @param reader Reader or buffer to decode from
         * @param [length] Message length if known beforehand
         * @returns Response
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): common.Response;

        /**
         * Decodes a Response message from the specified reader or buffer, length delimited.
         * @param reader Reader or buffer to decode from
         * @returns Response
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): common.Response;

        /**
         * Verifies a Response message.
         * @param message Plain object to verify
         * @returns `null` if valid, otherwise the reason why it is not
         */
        public static verify(message: { [k: string]: any }): (string|null);

        /**
         * Creates a Response message from a plain object. Also converts values to their respective internal types.
         * @param object Plain object
         * @returns Response
         */
        public static fromObject(object: { [k: string]: any }): common.Response;

        /**
         * Creates a plain object from a Response message. Also converts values to other types if specified.
         * @param message Response
         * @param [options] Conversion options
         * @returns Plain object
         */
        public static toObject(message: common.Response, options?: $protobuf.IConversionOptions): { [k: string]: any };

        /**
         * Converts this Response to JSON.
         * @returns JSON object
         */
        public toJSON(): { [k: string]: any };

        /**
         * Gets the default type url for Response
         * @param [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
         * @returns The default type url
         */
        public static getTypeUrl(typeUrlPrefix?: string): string;
    }

    /** Properties of a KickOutUserResponse. */
    interface IKickOutUserResponse {

        /** KickOutUserResponse reason */
        reason?: (string|null);
    }

    /** Represents a KickOutUserResponse. */
    class KickOutUserResponse implements IKickOutUserResponse {

        /**
         * Constructs a new KickOutUserResponse.
         * @param [properties] Properties to set
         */
        constructor(properties?: common.IKickOutUserResponse);

        /** KickOutUserResponse reason. */
        public reason: string;

        /**
         * Creates a new KickOutUserResponse instance using the specified properties.
         * @param [properties] Properties to set
         * @returns KickOutUserResponse instance
         */
        public static create(properties?: common.IKickOutUserResponse): common.KickOutUserResponse;

        /**
         * Encodes the specified KickOutUserResponse message. Does not implicitly {@link common.KickOutUserResponse.verify|verify} messages.
         * @param message KickOutUserResponse message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encode(message: common.IKickOutUserResponse, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Encodes the specified KickOutUserResponse message, length delimited. Does not implicitly {@link common.KickOutUserResponse.verify|verify} messages.
         * @param message KickOutUserResponse message or plain object to encode
         * @param [writer] Writer to encode to
         * @returns Writer
         */
        public static encodeDelimited(message: common.IKickOutUserResponse, writer?: $protobuf.Writer): $protobuf.Writer;

        /**
         * Decodes a KickOutUserResponse message from the specified reader or buffer.
         * @param reader Reader or buffer to decode from
         * @param [length] Message length if known beforehand
         * @returns KickOutUserResponse
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decode(reader: ($protobuf.Reader|Uint8Array), length?: number): common.KickOutUserResponse;

        /**
         * Decodes a KickOutUserResponse message from the specified reader or buffer, length delimited.
         * @param reader Reader or buffer to decode from
         * @returns KickOutUserResponse
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        public static decodeDelimited(reader: ($protobuf.Reader|Uint8Array)): common.KickOutUserResponse;

        /**
         * Verifies a KickOutUserResponse message.
         * @param message Plain object to verify
         * @returns `null` if valid, otherwise the reason why it is not
         */
        public static verify(message: { [k: string]: any }): (string|null);

        /**
         * Creates a KickOutUserResponse message from a plain object. Also converts values to their respective internal types.
         * @param object Plain object
         * @returns KickOutUserResponse
         */
        public static fromObject(object: { [k: string]: any }): common.KickOutUserResponse;

        /**
         * Creates a plain object from a KickOutUserResponse message. Also converts values to other types if specified.
         * @param message KickOutUserResponse
         * @param [options] Conversion options
         * @returns Plain object
         */
        public static toObject(message: common.KickOutUserResponse, options?: $protobuf.IConversionOptions): { [k: string]: any };

        /**
         * Converts this KickOutUserResponse to JSON.
         * @returns JSON object
         */
        public toJSON(): { [k: string]: any };

        /**
         * Gets the default type url for KickOutUserResponse
         * @param [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
         * @returns The default type url
         */
        public static getTypeUrl(typeUrlPrefix?: string): string;
    }
}
