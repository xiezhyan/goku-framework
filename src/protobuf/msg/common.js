/*eslint-disable block-scoped-var, id-length, no-control-regex, no-magic-numbers, no-prototype-builtins, no-redeclare, no-shadow, no-var, sort-vars*/
"use strict";

import * as  $protobuf from 'protobufjs/minimal';

// Common aliases
var $Reader = $protobuf.Reader, $Writer = $protobuf.Writer, $util = $protobuf.util;

// Exported root namespace
var $root = $protobuf.roots["default"] || ($protobuf.roots["default"] = {});

export const common = $root.common = (() => {

    /**
     * Namespace common.
     * @exports common
     * @namespace
     */
    var common = {};

    /**
     * CommonDef enum.
     * @name common.CommonDef
     * @enum {number}
     * @property {number} _Dummy=0 _Dummy value
     * @property {number} _PingRequest=1 _PingRequest value
     * @property {number} _PingResponse=2 _PingResponse value
     * @property {number} _Response=3 _Response value
     * @property {number} _KickOutUserResponse=4 _KickOutUserResponse value
     * @property {number} _CheckInTicketRequest=11 _CheckInTicketRequest value
     * @property {number} _CheckInTicketResponse=12 _CheckInTicketResponse value
     * @property {number} _ReconnRequest=13 _ReconnRequest value
     * @property {number} _ReconnResponse=14 _ReconnResponse value
     */
    common.CommonDef = (function() {
        var valuesById = {}, values = Object.create(valuesById);
        values[valuesById[0] = "_Dummy"] = 0;
        values[valuesById[1] = "_PingRequest"] = 1;
        values[valuesById[2] = "_PingResponse"] = 2;
        values[valuesById[3] = "_Response"] = 3;
        values[valuesById[4] = "_KickOutUserResponse"] = 4;
        values[valuesById[11] = "_CheckInTicketRequest"] = 11;
        values[valuesById[12] = "_CheckInTicketResponse"] = 12;
        values[valuesById[13] = "_ReconnRequest"] = 13;
        values[valuesById[14] = "_ReconnResponse"] = 14;
        return values;
    })();

    common.PingRequest = (function() {

        /**
         * Properties of a PingRequest.
         * @memberof common
         * @interface IPingRequest
         * @property {number|null} [pingId] PingRequest pingId
         */

        /**
         * Constructs a new PingRequest.
         * @memberof common
         * @classdesc Represents a PingRequest.
         * @implements IPingRequest
         * @constructor
         * @param {common.IPingRequest=} [properties] Properties to set
         */
        function PingRequest(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * PingRequest pingId.
         * @member {number} pingId
         * @memberof common.PingRequest
         * @instance
         */
        PingRequest.prototype.pingId = 0;

        /**
         * Creates a new PingRequest instance using the specified properties.
         * @function create
         * @memberof common.PingRequest
         * @static
         * @param {common.IPingRequest=} [properties] Properties to set
         * @returns {common.PingRequest} PingRequest instance
         */
        PingRequest.create = function create(properties) {
            return new PingRequest(properties);
        };

        /**
         * Encodes the specified PingRequest message. Does not implicitly {@link common.PingRequest.verify|verify} messages.
         * @function encode
         * @memberof common.PingRequest
         * @static
         * @param {common.IPingRequest} message PingRequest message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        PingRequest.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.pingId != null && Object.hasOwnProperty.call(message, "pingId"))
                writer.uint32(/* id 1, wireType 0 =*/8).sint32(message.pingId);
            return writer;
        };

        /**
         * Encodes the specified PingRequest message, length delimited. Does not implicitly {@link common.PingRequest.verify|verify} messages.
         * @function encodeDelimited
         * @memberof common.PingRequest
         * @static
         * @param {common.IPingRequest} message PingRequest message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        PingRequest.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a PingRequest message from the specified reader or buffer.
         * @function decode
         * @memberof common.PingRequest
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {common.PingRequest} PingRequest
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        PingRequest.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.common.PingRequest();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1: {
                        message.pingId = reader.sint32();
                        break;
                    }
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };

        /**
         * Decodes a PingRequest message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof common.PingRequest
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {common.PingRequest} PingRequest
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        PingRequest.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a PingRequest message.
         * @function verify
         * @memberof common.PingRequest
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        PingRequest.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (message.pingId != null && message.hasOwnProperty("pingId"))
                if (!$util.isInteger(message.pingId))
                    return "pingId: integer expected";
            return null;
        };

        /**
         * Creates a PingRequest message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof common.PingRequest
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {common.PingRequest} PingRequest
         */
        PingRequest.fromObject = function fromObject(object) {
            if (object instanceof $root.common.PingRequest)
                return object;
            var message = new $root.common.PingRequest();
            if (object.pingId != null)
                message.pingId = object.pingId | 0;
            return message;
        };

        /**
         * Creates a plain object from a PingRequest message. Also converts values to other types if specified.
         * @function toObject
         * @memberof common.PingRequest
         * @static
         * @param {common.PingRequest} message PingRequest
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        PingRequest.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults)
                object.pingId = 0;
            if (message.pingId != null && message.hasOwnProperty("pingId"))
                object.pingId = message.pingId;
            return object;
        };

        /**
         * Converts this PingRequest to JSON.
         * @function toJSON
         * @memberof common.PingRequest
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        PingRequest.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        /**
         * Gets the default type url for PingRequest
         * @function getTypeUrl
         * @memberof common.PingRequest
         * @static
         * @param {string} [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
         * @returns {string} The default type url
         */
        PingRequest.getTypeUrl = function getTypeUrl(typeUrlPrefix) {
            if (typeUrlPrefix === undefined) {
                typeUrlPrefix = "type.googleapis.com";
            }
            return typeUrlPrefix + "/common.PingRequest";
        };

        return PingRequest;
    })();

    common.PingResponse = (function() {

        /**
         * Properties of a PingResponse.
         * @memberof common
         * @interface IPingResponse
         * @property {number|null} [pingId] PingResponse pingId
         */

        /**
         * Constructs a new PingResponse.
         * @memberof common
         * @classdesc Represents a PingResponse.
         * @implements IPingResponse
         * @constructor
         * @param {common.IPingResponse=} [properties] Properties to set
         */
        function PingResponse(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * PingResponse pingId.
         * @member {number} pingId
         * @memberof common.PingResponse
         * @instance
         */
        PingResponse.prototype.pingId = 0;

        /**
         * Creates a new PingResponse instance using the specified properties.
         * @function create
         * @memberof common.PingResponse
         * @static
         * @param {common.IPingResponse=} [properties] Properties to set
         * @returns {common.PingResponse} PingResponse instance
         */
        PingResponse.create = function create(properties) {
            return new PingResponse(properties);
        };

        /**
         * Encodes the specified PingResponse message. Does not implicitly {@link common.PingResponse.verify|verify} messages.
         * @function encode
         * @memberof common.PingResponse
         * @static
         * @param {common.IPingResponse} message PingResponse message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        PingResponse.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.pingId != null && Object.hasOwnProperty.call(message, "pingId"))
                writer.uint32(/* id 1, wireType 0 =*/8).sint32(message.pingId);
            return writer;
        };

        /**
         * Encodes the specified PingResponse message, length delimited. Does not implicitly {@link common.PingResponse.verify|verify} messages.
         * @function encodeDelimited
         * @memberof common.PingResponse
         * @static
         * @param {common.IPingResponse} message PingResponse message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        PingResponse.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a PingResponse message from the specified reader or buffer.
         * @function decode
         * @memberof common.PingResponse
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {common.PingResponse} PingResponse
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        PingResponse.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.common.PingResponse();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1: {
                        message.pingId = reader.sint32();
                        break;
                    }
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };

        /**
         * Decodes a PingResponse message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof common.PingResponse
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {common.PingResponse} PingResponse
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        PingResponse.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a PingResponse message.
         * @function verify
         * @memberof common.PingResponse
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        PingResponse.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (message.pingId != null && message.hasOwnProperty("pingId"))
                if (!$util.isInteger(message.pingId))
                    return "pingId: integer expected";
            return null;
        };

        /**
         * Creates a PingResponse message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof common.PingResponse
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {common.PingResponse} PingResponse
         */
        PingResponse.fromObject = function fromObject(object) {
            if (object instanceof $root.common.PingResponse)
                return object;
            var message = new $root.common.PingResponse();
            if (object.pingId != null)
                message.pingId = object.pingId | 0;
            return message;
        };

        /**
         * Creates a plain object from a PingResponse message. Also converts values to other types if specified.
         * @function toObject
         * @memberof common.PingResponse
         * @static
         * @param {common.PingResponse} message PingResponse
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        PingResponse.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults)
                object.pingId = 0;
            if (message.pingId != null && message.hasOwnProperty("pingId"))
                object.pingId = message.pingId;
            return object;
        };

        /**
         * Converts this PingResponse to JSON.
         * @function toJSON
         * @memberof common.PingResponse
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        PingResponse.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        /**
         * Gets the default type url for PingResponse
         * @function getTypeUrl
         * @memberof common.PingResponse
         * @static
         * @param {string} [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
         * @returns {string} The default type url
         */
        PingResponse.getTypeUrl = function getTypeUrl(typeUrlPrefix) {
            if (typeUrlPrefix === undefined) {
                typeUrlPrefix = "type.googleapis.com";
            }
            return typeUrlPrefix + "/common.PingResponse";
        };

        return PingResponse;
    })();

    common.CheckInTicketRequest = (function() {

        /**
         * Properties of a CheckInTicketRequest.
         * @memberof common
         * @interface ICheckInTicketRequest
         * @property {number|Long|null} [userId] CheckInTicketRequest userId
         * @property {string|null} [ticket] CheckInTicketRequest ticket
         */

        /**
         * Constructs a new CheckInTicketRequest.
         * @memberof common
         * @classdesc Represents a CheckInTicketRequest.
         * @implements ICheckInTicketRequest
         * @constructor
         * @param {common.ICheckInTicketRequest=} [properties] Properties to set
         */
        function CheckInTicketRequest(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * CheckInTicketRequest userId.
         * @member {number|Long} userId
         * @memberof common.CheckInTicketRequest
         * @instance
         */
        CheckInTicketRequest.prototype.userId = $util.Long ? $util.Long.fromBits(0,0,false) : 0;

        /**
         * CheckInTicketRequest ticket.
         * @member {string} ticket
         * @memberof common.CheckInTicketRequest
         * @instance
         */
        CheckInTicketRequest.prototype.ticket = "";

        /**
         * Creates a new CheckInTicketRequest instance using the specified properties.
         * @function create
         * @memberof common.CheckInTicketRequest
         * @static
         * @param {common.ICheckInTicketRequest=} [properties] Properties to set
         * @returns {common.CheckInTicketRequest} CheckInTicketRequest instance
         */
        CheckInTicketRequest.create = function create(properties) {
            return new CheckInTicketRequest(properties);
        };

        /**
         * Encodes the specified CheckInTicketRequest message. Does not implicitly {@link common.CheckInTicketRequest.verify|verify} messages.
         * @function encode
         * @memberof common.CheckInTicketRequest
         * @static
         * @param {common.ICheckInTicketRequest} message CheckInTicketRequest message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        CheckInTicketRequest.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.userId != null && Object.hasOwnProperty.call(message, "userId"))
                writer.uint32(/* id 1, wireType 0 =*/8).int64(message.userId);
            if (message.ticket != null && Object.hasOwnProperty.call(message, "ticket"))
                writer.uint32(/* id 2, wireType 2 =*/18).string(message.ticket);
            return writer;
        };

        /**
         * Encodes the specified CheckInTicketRequest message, length delimited. Does not implicitly {@link common.CheckInTicketRequest.verify|verify} messages.
         * @function encodeDelimited
         * @memberof common.CheckInTicketRequest
         * @static
         * @param {common.ICheckInTicketRequest} message CheckInTicketRequest message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        CheckInTicketRequest.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a CheckInTicketRequest message from the specified reader or buffer.
         * @function decode
         * @memberof common.CheckInTicketRequest
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {common.CheckInTicketRequest} CheckInTicketRequest
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        CheckInTicketRequest.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.common.CheckInTicketRequest();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1: {
                        message.userId = reader.int64();
                        break;
                    }
                case 2: {
                        message.ticket = reader.string();
                        break;
                    }
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };

        /**
         * Decodes a CheckInTicketRequest message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof common.CheckInTicketRequest
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {common.CheckInTicketRequest} CheckInTicketRequest
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        CheckInTicketRequest.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a CheckInTicketRequest message.
         * @function verify
         * @memberof common.CheckInTicketRequest
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        CheckInTicketRequest.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (message.userId != null && message.hasOwnProperty("userId"))
                if (!$util.isInteger(message.userId) && !(message.userId && $util.isInteger(message.userId.low) && $util.isInteger(message.userId.high)))
                    return "userId: integer|Long expected";
            if (message.ticket != null && message.hasOwnProperty("ticket"))
                if (!$util.isString(message.ticket))
                    return "ticket: string expected";
            return null;
        };

        /**
         * Creates a CheckInTicketRequest message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof common.CheckInTicketRequest
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {common.CheckInTicketRequest} CheckInTicketRequest
         */
        CheckInTicketRequest.fromObject = function fromObject(object) {
            if (object instanceof $root.common.CheckInTicketRequest)
                return object;
            var message = new $root.common.CheckInTicketRequest();
            if (object.userId != null)
                if ($util.Long)
                    (message.userId = $util.Long.fromValue(object.userId)).unsigned = false;
                else if (typeof object.userId === "string")
                    message.userId = parseInt(object.userId, 10);
                else if (typeof object.userId === "number")
                    message.userId = object.userId;
                else if (typeof object.userId === "object")
                    message.userId = new $util.LongBits(object.userId.low >>> 0, object.userId.high >>> 0).toNumber();
            if (object.ticket != null)
                message.ticket = String(object.ticket);
            return message;
        };

        /**
         * Creates a plain object from a CheckInTicketRequest message. Also converts values to other types if specified.
         * @function toObject
         * @memberof common.CheckInTicketRequest
         * @static
         * @param {common.CheckInTicketRequest} message CheckInTicketRequest
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        CheckInTicketRequest.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults) {
                if ($util.Long) {
                    var long = new $util.Long(0, 0, false);
                    object.userId = options.longs === String ? long.toString() : options.longs === Number ? long.toNumber() : long;
                } else
                    object.userId = options.longs === String ? "0" : 0;
                object.ticket = "";
            }
            if (message.userId != null && message.hasOwnProperty("userId"))
                if (typeof message.userId === "number")
                    object.userId = options.longs === String ? String(message.userId) : message.userId;
                else
                    object.userId = options.longs === String ? $util.Long.prototype.toString.call(message.userId) : options.longs === Number ? new $util.LongBits(message.userId.low >>> 0, message.userId.high >>> 0).toNumber() : message.userId;
            if (message.ticket != null && message.hasOwnProperty("ticket"))
                object.ticket = message.ticket;
            return object;
        };

        /**
         * Converts this CheckInTicketRequest to JSON.
         * @function toJSON
         * @memberof common.CheckInTicketRequest
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        CheckInTicketRequest.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        /**
         * Gets the default type url for CheckInTicketRequest
         * @function getTypeUrl
         * @memberof common.CheckInTicketRequest
         * @static
         * @param {string} [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
         * @returns {string} The default type url
         */
        CheckInTicketRequest.getTypeUrl = function getTypeUrl(typeUrlPrefix) {
            if (typeUrlPrefix === undefined) {
                typeUrlPrefix = "type.googleapis.com";
            }
            return typeUrlPrefix + "/common.CheckInTicketRequest";
        };

        return CheckInTicketRequest;
    })();

    common.CheckInTicketResponse = (function() {

        /**
         * Properties of a CheckInTicketResponse.
         * @memberof common
         * @interface ICheckInTicketResponse
         * @property {number|Long|null} [userId] CheckInTicketResponse userId
         * @property {boolean|null} [succezz] CheckInTicketResponse succezz
         */

        /**
         * Constructs a new CheckInTicketResponse.
         * @memberof common
         * @classdesc Represents a CheckInTicketResponse.
         * @implements ICheckInTicketResponse
         * @constructor
         * @param {common.ICheckInTicketResponse=} [properties] Properties to set
         */
        function CheckInTicketResponse(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * CheckInTicketResponse userId.
         * @member {number|Long} userId
         * @memberof common.CheckInTicketResponse
         * @instance
         */
        CheckInTicketResponse.prototype.userId = $util.Long ? $util.Long.fromBits(0,0,false) : 0;

        /**
         * CheckInTicketResponse succezz.
         * @member {boolean} succezz
         * @memberof common.CheckInTicketResponse
         * @instance
         */
        CheckInTicketResponse.prototype.succezz = false;

        /**
         * Creates a new CheckInTicketResponse instance using the specified properties.
         * @function create
         * @memberof common.CheckInTicketResponse
         * @static
         * @param {common.ICheckInTicketResponse=} [properties] Properties to set
         * @returns {common.CheckInTicketResponse} CheckInTicketResponse instance
         */
        CheckInTicketResponse.create = function create(properties) {
            return new CheckInTicketResponse(properties);
        };

        /**
         * Encodes the specified CheckInTicketResponse message. Does not implicitly {@link common.CheckInTicketResponse.verify|verify} messages.
         * @function encode
         * @memberof common.CheckInTicketResponse
         * @static
         * @param {common.ICheckInTicketResponse} message CheckInTicketResponse message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        CheckInTicketResponse.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.userId != null && Object.hasOwnProperty.call(message, "userId"))
                writer.uint32(/* id 1, wireType 0 =*/8).int64(message.userId);
            if (message.succezz != null && Object.hasOwnProperty.call(message, "succezz"))
                writer.uint32(/* id 2, wireType 0 =*/16).bool(message.succezz);
            return writer;
        };

        /**
         * Encodes the specified CheckInTicketResponse message, length delimited. Does not implicitly {@link common.CheckInTicketResponse.verify|verify} messages.
         * @function encodeDelimited
         * @memberof common.CheckInTicketResponse
         * @static
         * @param {common.ICheckInTicketResponse} message CheckInTicketResponse message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        CheckInTicketResponse.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a CheckInTicketResponse message from the specified reader or buffer.
         * @function decode
         * @memberof common.CheckInTicketResponse
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {common.CheckInTicketResponse} CheckInTicketResponse
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        CheckInTicketResponse.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.common.CheckInTicketResponse();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1: {
                        message.userId = reader.int64();
                        break;
                    }
                case 2: {
                        message.succezz = reader.bool();
                        break;
                    }
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };

        /**
         * Decodes a CheckInTicketResponse message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof common.CheckInTicketResponse
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {common.CheckInTicketResponse} CheckInTicketResponse
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        CheckInTicketResponse.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a CheckInTicketResponse message.
         * @function verify
         * @memberof common.CheckInTicketResponse
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        CheckInTicketResponse.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (message.userId != null && message.hasOwnProperty("userId"))
                if (!$util.isInteger(message.userId) && !(message.userId && $util.isInteger(message.userId.low) && $util.isInteger(message.userId.high)))
                    return "userId: integer|Long expected";
            if (message.succezz != null && message.hasOwnProperty("succezz"))
                if (typeof message.succezz !== "boolean")
                    return "succezz: boolean expected";
            return null;
        };

        /**
         * Creates a CheckInTicketResponse message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof common.CheckInTicketResponse
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {common.CheckInTicketResponse} CheckInTicketResponse
         */
        CheckInTicketResponse.fromObject = function fromObject(object) {
            if (object instanceof $root.common.CheckInTicketResponse)
                return object;
            var message = new $root.common.CheckInTicketResponse();
            if (object.userId != null)
                if ($util.Long)
                    (message.userId = $util.Long.fromValue(object.userId)).unsigned = false;
                else if (typeof object.userId === "string")
                    message.userId = parseInt(object.userId, 10);
                else if (typeof object.userId === "number")
                    message.userId = object.userId;
                else if (typeof object.userId === "object")
                    message.userId = new $util.LongBits(object.userId.low >>> 0, object.userId.high >>> 0).toNumber();
            if (object.succezz != null)
                message.succezz = Boolean(object.succezz);
            return message;
        };

        /**
         * Creates a plain object from a CheckInTicketResponse message. Also converts values to other types if specified.
         * @function toObject
         * @memberof common.CheckInTicketResponse
         * @static
         * @param {common.CheckInTicketResponse} message CheckInTicketResponse
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        CheckInTicketResponse.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults) {
                if ($util.Long) {
                    var long = new $util.Long(0, 0, false);
                    object.userId = options.longs === String ? long.toString() : options.longs === Number ? long.toNumber() : long;
                } else
                    object.userId = options.longs === String ? "0" : 0;
                object.succezz = false;
            }
            if (message.userId != null && message.hasOwnProperty("userId"))
                if (typeof message.userId === "number")
                    object.userId = options.longs === String ? String(message.userId) : message.userId;
                else
                    object.userId = options.longs === String ? $util.Long.prototype.toString.call(message.userId) : options.longs === Number ? new $util.LongBits(message.userId.low >>> 0, message.userId.high >>> 0).toNumber() : message.userId;
            if (message.succezz != null && message.hasOwnProperty("succezz"))
                object.succezz = message.succezz;
            return object;
        };

        /**
         * Converts this CheckInTicketResponse to JSON.
         * @function toJSON
         * @memberof common.CheckInTicketResponse
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        CheckInTicketResponse.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        /**
         * Gets the default type url for CheckInTicketResponse
         * @function getTypeUrl
         * @memberof common.CheckInTicketResponse
         * @static
         * @param {string} [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
         * @returns {string} The default type url
         */
        CheckInTicketResponse.getTypeUrl = function getTypeUrl(typeUrlPrefix) {
            if (typeUrlPrefix === undefined) {
                typeUrlPrefix = "type.googleapis.com";
            }
            return typeUrlPrefix + "/common.CheckInTicketResponse";
        };

        return CheckInTicketResponse;
    })();

    common.ReconnRequest = (function() {

        /**
         * Properties of a ReconnRequest.
         * @memberof common
         * @interface IReconnRequest
         * @property {number|Long|null} [userId] ReconnRequest userId
         * @property {string|null} [ukey] ReconnRequest ukey
         * @property {number|Long|null} [ukeyExpireAt] ReconnRequest ukeyExpireAt
         */

        /**
         * Constructs a new ReconnRequest.
         * @memberof common
         * @classdesc Represents a ReconnRequest.
         * @implements IReconnRequest
         * @constructor
         * @param {common.IReconnRequest=} [properties] Properties to set
         */
        function ReconnRequest(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * ReconnRequest userId.
         * @member {number|Long} userId
         * @memberof common.ReconnRequest
         * @instance
         */
        ReconnRequest.prototype.userId = $util.Long ? $util.Long.fromBits(0,0,false) : 0;

        /**
         * ReconnRequest ukey.
         * @member {string} ukey
         * @memberof common.ReconnRequest
         * @instance
         */
        ReconnRequest.prototype.ukey = "";

        /**
         * ReconnRequest ukeyExpireAt.
         * @member {number|Long} ukeyExpireAt
         * @memberof common.ReconnRequest
         * @instance
         */
        ReconnRequest.prototype.ukeyExpireAt = $util.Long ? $util.Long.fromBits(0,0,false) : 0;

        /**
         * Creates a new ReconnRequest instance using the specified properties.
         * @function create
         * @memberof common.ReconnRequest
         * @static
         * @param {common.IReconnRequest=} [properties] Properties to set
         * @returns {common.ReconnRequest} ReconnRequest instance
         */
        ReconnRequest.create = function create(properties) {
            return new ReconnRequest(properties);
        };

        /**
         * Encodes the specified ReconnRequest message. Does not implicitly {@link common.ReconnRequest.verify|verify} messages.
         * @function encode
         * @memberof common.ReconnRequest
         * @static
         * @param {common.IReconnRequest} message ReconnRequest message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        ReconnRequest.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.userId != null && Object.hasOwnProperty.call(message, "userId"))
                writer.uint32(/* id 1, wireType 0 =*/8).int64(message.userId);
            if (message.ukey != null && Object.hasOwnProperty.call(message, "ukey"))
                writer.uint32(/* id 2, wireType 2 =*/18).string(message.ukey);
            if (message.ukeyExpireAt != null && Object.hasOwnProperty.call(message, "ukeyExpireAt"))
                writer.uint32(/* id 3, wireType 0 =*/24).sint64(message.ukeyExpireAt);
            return writer;
        };

        /**
         * Encodes the specified ReconnRequest message, length delimited. Does not implicitly {@link common.ReconnRequest.verify|verify} messages.
         * @function encodeDelimited
         * @memberof common.ReconnRequest
         * @static
         * @param {common.IReconnRequest} message ReconnRequest message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        ReconnRequest.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a ReconnRequest message from the specified reader or buffer.
         * @function decode
         * @memberof common.ReconnRequest
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {common.ReconnRequest} ReconnRequest
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        ReconnRequest.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.common.ReconnRequest();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1: {
                        message.userId = reader.int64();
                        break;
                    }
                case 2: {
                        message.ukey = reader.string();
                        break;
                    }
                case 3: {
                        message.ukeyExpireAt = reader.sint64();
                        break;
                    }
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };

        /**
         * Decodes a ReconnRequest message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof common.ReconnRequest
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {common.ReconnRequest} ReconnRequest
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        ReconnRequest.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a ReconnRequest message.
         * @function verify
         * @memberof common.ReconnRequest
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        ReconnRequest.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (message.userId != null && message.hasOwnProperty("userId"))
                if (!$util.isInteger(message.userId) && !(message.userId && $util.isInteger(message.userId.low) && $util.isInteger(message.userId.high)))
                    return "userId: integer|Long expected";
            if (message.ukey != null && message.hasOwnProperty("ukey"))
                if (!$util.isString(message.ukey))
                    return "ukey: string expected";
            if (message.ukeyExpireAt != null && message.hasOwnProperty("ukeyExpireAt"))
                if (!$util.isInteger(message.ukeyExpireAt) && !(message.ukeyExpireAt && $util.isInteger(message.ukeyExpireAt.low) && $util.isInteger(message.ukeyExpireAt.high)))
                    return "ukeyExpireAt: integer|Long expected";
            return null;
        };

        /**
         * Creates a ReconnRequest message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof common.ReconnRequest
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {common.ReconnRequest} ReconnRequest
         */
        ReconnRequest.fromObject = function fromObject(object) {
            if (object instanceof $root.common.ReconnRequest)
                return object;
            var message = new $root.common.ReconnRequest();
            if (object.userId != null)
                if ($util.Long)
                    (message.userId = $util.Long.fromValue(object.userId)).unsigned = false;
                else if (typeof object.userId === "string")
                    message.userId = parseInt(object.userId, 10);
                else if (typeof object.userId === "number")
                    message.userId = object.userId;
                else if (typeof object.userId === "object")
                    message.userId = new $util.LongBits(object.userId.low >>> 0, object.userId.high >>> 0).toNumber();
            if (object.ukey != null)
                message.ukey = String(object.ukey);
            if (object.ukeyExpireAt != null)
                if ($util.Long)
                    (message.ukeyExpireAt = $util.Long.fromValue(object.ukeyExpireAt)).unsigned = false;
                else if (typeof object.ukeyExpireAt === "string")
                    message.ukeyExpireAt = parseInt(object.ukeyExpireAt, 10);
                else if (typeof object.ukeyExpireAt === "number")
                    message.ukeyExpireAt = object.ukeyExpireAt;
                else if (typeof object.ukeyExpireAt === "object")
                    message.ukeyExpireAt = new $util.LongBits(object.ukeyExpireAt.low >>> 0, object.ukeyExpireAt.high >>> 0).toNumber();
            return message;
        };

        /**
         * Creates a plain object from a ReconnRequest message. Also converts values to other types if specified.
         * @function toObject
         * @memberof common.ReconnRequest
         * @static
         * @param {common.ReconnRequest} message ReconnRequest
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        ReconnRequest.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults) {
                if ($util.Long) {
                    var long = new $util.Long(0, 0, false);
                    object.userId = options.longs === String ? long.toString() : options.longs === Number ? long.toNumber() : long;
                } else
                    object.userId = options.longs === String ? "0" : 0;
                object.ukey = "";
                if ($util.Long) {
                    var long = new $util.Long(0, 0, false);
                    object.ukeyExpireAt = options.longs === String ? long.toString() : options.longs === Number ? long.toNumber() : long;
                } else
                    object.ukeyExpireAt = options.longs === String ? "0" : 0;
            }
            if (message.userId != null && message.hasOwnProperty("userId"))
                if (typeof message.userId === "number")
                    object.userId = options.longs === String ? String(message.userId) : message.userId;
                else
                    object.userId = options.longs === String ? $util.Long.prototype.toString.call(message.userId) : options.longs === Number ? new $util.LongBits(message.userId.low >>> 0, message.userId.high >>> 0).toNumber() : message.userId;
            if (message.ukey != null && message.hasOwnProperty("ukey"))
                object.ukey = message.ukey;
            if (message.ukeyExpireAt != null && message.hasOwnProperty("ukeyExpireAt"))
                if (typeof message.ukeyExpireAt === "number")
                    object.ukeyExpireAt = options.longs === String ? String(message.ukeyExpireAt) : message.ukeyExpireAt;
                else
                    object.ukeyExpireAt = options.longs === String ? $util.Long.prototype.toString.call(message.ukeyExpireAt) : options.longs === Number ? new $util.LongBits(message.ukeyExpireAt.low >>> 0, message.ukeyExpireAt.high >>> 0).toNumber() : message.ukeyExpireAt;
            return object;
        };

        /**
         * Converts this ReconnRequest to JSON.
         * @function toJSON
         * @memberof common.ReconnRequest
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        ReconnRequest.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        /**
         * Gets the default type url for ReconnRequest
         * @function getTypeUrl
         * @memberof common.ReconnRequest
         * @static
         * @param {string} [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
         * @returns {string} The default type url
         */
        ReconnRequest.getTypeUrl = function getTypeUrl(typeUrlPrefix) {
            if (typeUrlPrefix === undefined) {
                typeUrlPrefix = "type.googleapis.com";
            }
            return typeUrlPrefix + "/common.ReconnRequest";
        };

        return ReconnRequest;
    })();

    common.ReconnResponse = (function() {

        /**
         * Properties of a ReconnResponse.
         * @memberof common
         * @interface IReconnResponse
         * @property {number|Long|null} [userId] ReconnResponse userId
         * @property {string|null} [ukey] ReconnResponse ukey
         * @property {number|Long|null} [ukeyExpire] ReconnResponse ukeyExpire
         * @property {boolean|null} [ok] ReconnResponse ok
         */

        /**
         * Constructs a new ReconnResponse.
         * @memberof common
         * @classdesc Represents a ReconnResponse.
         * @implements IReconnResponse
         * @constructor
         * @param {common.IReconnResponse=} [properties] Properties to set
         */
        function ReconnResponse(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * ReconnResponse userId.
         * @member {number|Long} userId
         * @memberof common.ReconnResponse
         * @instance
         */
        ReconnResponse.prototype.userId = $util.Long ? $util.Long.fromBits(0,0,false) : 0;

        /**
         * ReconnResponse ukey.
         * @member {string} ukey
         * @memberof common.ReconnResponse
         * @instance
         */
        ReconnResponse.prototype.ukey = "";

        /**
         * ReconnResponse ukeyExpire.
         * @member {number|Long} ukeyExpire
         * @memberof common.ReconnResponse
         * @instance
         */
        ReconnResponse.prototype.ukeyExpire = $util.Long ? $util.Long.fromBits(0,0,false) : 0;

        /**
         * ReconnResponse ok.
         * @member {boolean} ok
         * @memberof common.ReconnResponse
         * @instance
         */
        ReconnResponse.prototype.ok = false;

        /**
         * Creates a new ReconnResponse instance using the specified properties.
         * @function create
         * @memberof common.ReconnResponse
         * @static
         * @param {common.IReconnResponse=} [properties] Properties to set
         * @returns {common.ReconnResponse} ReconnResponse instance
         */
        ReconnResponse.create = function create(properties) {
            return new ReconnResponse(properties);
        };

        /**
         * Encodes the specified ReconnResponse message. Does not implicitly {@link common.ReconnResponse.verify|verify} messages.
         * @function encode
         * @memberof common.ReconnResponse
         * @static
         * @param {common.IReconnResponse} message ReconnResponse message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        ReconnResponse.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.userId != null && Object.hasOwnProperty.call(message, "userId"))
                writer.uint32(/* id 1, wireType 0 =*/8).int64(message.userId);
            if (message.ukey != null && Object.hasOwnProperty.call(message, "ukey"))
                writer.uint32(/* id 2, wireType 2 =*/18).string(message.ukey);
            if (message.ukeyExpire != null && Object.hasOwnProperty.call(message, "ukeyExpire"))
                writer.uint32(/* id 3, wireType 0 =*/24).sint64(message.ukeyExpire);
            if (message.ok != null && Object.hasOwnProperty.call(message, "ok"))
                writer.uint32(/* id 4, wireType 0 =*/32).bool(message.ok);
            return writer;
        };

        /**
         * Encodes the specified ReconnResponse message, length delimited. Does not implicitly {@link common.ReconnResponse.verify|verify} messages.
         * @function encodeDelimited
         * @memberof common.ReconnResponse
         * @static
         * @param {common.IReconnResponse} message ReconnResponse message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        ReconnResponse.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a ReconnResponse message from the specified reader or buffer.
         * @function decode
         * @memberof common.ReconnResponse
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {common.ReconnResponse} ReconnResponse
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        ReconnResponse.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.common.ReconnResponse();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1: {
                        message.userId = reader.int64();
                        break;
                    }
                case 2: {
                        message.ukey = reader.string();
                        break;
                    }
                case 3: {
                        message.ukeyExpire = reader.sint64();
                        break;
                    }
                case 4: {
                        message.ok = reader.bool();
                        break;
                    }
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };

        /**
         * Decodes a ReconnResponse message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof common.ReconnResponse
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {common.ReconnResponse} ReconnResponse
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        ReconnResponse.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a ReconnResponse message.
         * @function verify
         * @memberof common.ReconnResponse
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        ReconnResponse.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (message.userId != null && message.hasOwnProperty("userId"))
                if (!$util.isInteger(message.userId) && !(message.userId && $util.isInteger(message.userId.low) && $util.isInteger(message.userId.high)))
                    return "userId: integer|Long expected";
            if (message.ukey != null && message.hasOwnProperty("ukey"))
                if (!$util.isString(message.ukey))
                    return "ukey: string expected";
            if (message.ukeyExpire != null && message.hasOwnProperty("ukeyExpire"))
                if (!$util.isInteger(message.ukeyExpire) && !(message.ukeyExpire && $util.isInteger(message.ukeyExpire.low) && $util.isInteger(message.ukeyExpire.high)))
                    return "ukeyExpire: integer|Long expected";
            if (message.ok != null && message.hasOwnProperty("ok"))
                if (typeof message.ok !== "boolean")
                    return "ok: boolean expected";
            return null;
        };

        /**
         * Creates a ReconnResponse message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof common.ReconnResponse
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {common.ReconnResponse} ReconnResponse
         */
        ReconnResponse.fromObject = function fromObject(object) {
            if (object instanceof $root.common.ReconnResponse)
                return object;
            var message = new $root.common.ReconnResponse();
            if (object.userId != null)
                if ($util.Long)
                    (message.userId = $util.Long.fromValue(object.userId)).unsigned = false;
                else if (typeof object.userId === "string")
                    message.userId = parseInt(object.userId, 10);
                else if (typeof object.userId === "number")
                    message.userId = object.userId;
                else if (typeof object.userId === "object")
                    message.userId = new $util.LongBits(object.userId.low >>> 0, object.userId.high >>> 0).toNumber();
            if (object.ukey != null)
                message.ukey = String(object.ukey);
            if (object.ukeyExpire != null)
                if ($util.Long)
                    (message.ukeyExpire = $util.Long.fromValue(object.ukeyExpire)).unsigned = false;
                else if (typeof object.ukeyExpire === "string")
                    message.ukeyExpire = parseInt(object.ukeyExpire, 10);
                else if (typeof object.ukeyExpire === "number")
                    message.ukeyExpire = object.ukeyExpire;
                else if (typeof object.ukeyExpire === "object")
                    message.ukeyExpire = new $util.LongBits(object.ukeyExpire.low >>> 0, object.ukeyExpire.high >>> 0).toNumber();
            if (object.ok != null)
                message.ok = Boolean(object.ok);
            return message;
        };

        /**
         * Creates a plain object from a ReconnResponse message. Also converts values to other types if specified.
         * @function toObject
         * @memberof common.ReconnResponse
         * @static
         * @param {common.ReconnResponse} message ReconnResponse
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        ReconnResponse.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults) {
                if ($util.Long) {
                    var long = new $util.Long(0, 0, false);
                    object.userId = options.longs === String ? long.toString() : options.longs === Number ? long.toNumber() : long;
                } else
                    object.userId = options.longs === String ? "0" : 0;
                object.ukey = "";
                if ($util.Long) {
                    var long = new $util.Long(0, 0, false);
                    object.ukeyExpire = options.longs === String ? long.toString() : options.longs === Number ? long.toNumber() : long;
                } else
                    object.ukeyExpire = options.longs === String ? "0" : 0;
                object.ok = false;
            }
            if (message.userId != null && message.hasOwnProperty("userId"))
                if (typeof message.userId === "number")
                    object.userId = options.longs === String ? String(message.userId) : message.userId;
                else
                    object.userId = options.longs === String ? $util.Long.prototype.toString.call(message.userId) : options.longs === Number ? new $util.LongBits(message.userId.low >>> 0, message.userId.high >>> 0).toNumber() : message.userId;
            if (message.ukey != null && message.hasOwnProperty("ukey"))
                object.ukey = message.ukey;
            if (message.ukeyExpire != null && message.hasOwnProperty("ukeyExpire"))
                if (typeof message.ukeyExpire === "number")
                    object.ukeyExpire = options.longs === String ? String(message.ukeyExpire) : message.ukeyExpire;
                else
                    object.ukeyExpire = options.longs === String ? $util.Long.prototype.toString.call(message.ukeyExpire) : options.longs === Number ? new $util.LongBits(message.ukeyExpire.low >>> 0, message.ukeyExpire.high >>> 0).toNumber() : message.ukeyExpire;
            if (message.ok != null && message.hasOwnProperty("ok"))
                object.ok = message.ok;
            return object;
        };

        /**
         * Converts this ReconnResponse to JSON.
         * @function toJSON
         * @memberof common.ReconnResponse
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        ReconnResponse.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        /**
         * Gets the default type url for ReconnResponse
         * @function getTypeUrl
         * @memberof common.ReconnResponse
         * @static
         * @param {string} [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
         * @returns {string} The default type url
         */
        ReconnResponse.getTypeUrl = function getTypeUrl(typeUrlPrefix) {
            if (typeUrlPrefix === undefined) {
                typeUrlPrefix = "type.googleapis.com";
            }
            return typeUrlPrefix + "/common.ReconnResponse";
        };

        return ReconnResponse;
    })();

    common.Response = (function() {

        /**
         * Properties of a Response.
         * @memberof common
         * @interface IResponse
         * @property {number|null} [code] Response code
         * @property {string|null} [msg] Response msg
         */

        /**
         * Constructs a new Response.
         * @memberof common
         * @classdesc Represents a Response.
         * @implements IResponse
         * @constructor
         * @param {common.IResponse=} [properties] Properties to set
         */
        function Response(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * Response code.
         * @member {number} code
         * @memberof common.Response
         * @instance
         */
        Response.prototype.code = 0;

        /**
         * Response msg.
         * @member {string} msg
         * @memberof common.Response
         * @instance
         */
        Response.prototype.msg = "";

        /**
         * Creates a new Response instance using the specified properties.
         * @function create
         * @memberof common.Response
         * @static
         * @param {common.IResponse=} [properties] Properties to set
         * @returns {common.Response} Response instance
         */
        Response.create = function create(properties) {
            return new Response(properties);
        };

        /**
         * Encodes the specified Response message. Does not implicitly {@link common.Response.verify|verify} messages.
         * @function encode
         * @memberof common.Response
         * @static
         * @param {common.IResponse} message Response message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        Response.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.code != null && Object.hasOwnProperty.call(message, "code"))
                writer.uint32(/* id 1, wireType 0 =*/8).sint32(message.code);
            if (message.msg != null && Object.hasOwnProperty.call(message, "msg"))
                writer.uint32(/* id 2, wireType 2 =*/18).string(message.msg);
            return writer;
        };

        /**
         * Encodes the specified Response message, length delimited. Does not implicitly {@link common.Response.verify|verify} messages.
         * @function encodeDelimited
         * @memberof common.Response
         * @static
         * @param {common.IResponse} message Response message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        Response.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a Response message from the specified reader or buffer.
         * @function decode
         * @memberof common.Response
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {common.Response} Response
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        Response.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.common.Response();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1: {
                        message.code = reader.sint32();
                        break;
                    }
                case 2: {
                        message.msg = reader.string();
                        break;
                    }
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };

        /**
         * Decodes a Response message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof common.Response
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {common.Response} Response
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        Response.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a Response message.
         * @function verify
         * @memberof common.Response
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        Response.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (message.code != null && message.hasOwnProperty("code"))
                if (!$util.isInteger(message.code))
                    return "code: integer expected";
            if (message.msg != null && message.hasOwnProperty("msg"))
                if (!$util.isString(message.msg))
                    return "msg: string expected";
            return null;
        };

        /**
         * Creates a Response message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof common.Response
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {common.Response} Response
         */
        Response.fromObject = function fromObject(object) {
            if (object instanceof $root.common.Response)
                return object;
            var message = new $root.common.Response();
            if (object.code != null)
                message.code = object.code | 0;
            if (object.msg != null)
                message.msg = String(object.msg);
            return message;
        };

        /**
         * Creates a plain object from a Response message. Also converts values to other types if specified.
         * @function toObject
         * @memberof common.Response
         * @static
         * @param {common.Response} message Response
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        Response.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults) {
                object.code = 0;
                object.msg = "";
            }
            if (message.code != null && message.hasOwnProperty("code"))
                object.code = message.code;
            if (message.msg != null && message.hasOwnProperty("msg"))
                object.msg = message.msg;
            return object;
        };

        /**
         * Converts this Response to JSON.
         * @function toJSON
         * @memberof common.Response
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        Response.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        /**
         * Gets the default type url for Response
         * @function getTypeUrl
         * @memberof common.Response
         * @static
         * @param {string} [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
         * @returns {string} The default type url
         */
        Response.getTypeUrl = function getTypeUrl(typeUrlPrefix) {
            if (typeUrlPrefix === undefined) {
                typeUrlPrefix = "type.googleapis.com";
            }
            return typeUrlPrefix + "/common.Response";
        };

        return Response;
    })();

    common.KickOutUserResponse = (function() {

        /**
         * Properties of a KickOutUserResponse.
         * @memberof common
         * @interface IKickOutUserResponse
         * @property {string|null} [reason] KickOutUserResponse reason
         */

        /**
         * Constructs a new KickOutUserResponse.
         * @memberof common
         * @classdesc Represents a KickOutUserResponse.
         * @implements IKickOutUserResponse
         * @constructor
         * @param {common.IKickOutUserResponse=} [properties] Properties to set
         */
        function KickOutUserResponse(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }

        /**
         * KickOutUserResponse reason.
         * @member {string} reason
         * @memberof common.KickOutUserResponse
         * @instance
         */
        KickOutUserResponse.prototype.reason = "";

        /**
         * Creates a new KickOutUserResponse instance using the specified properties.
         * @function create
         * @memberof common.KickOutUserResponse
         * @static
         * @param {common.IKickOutUserResponse=} [properties] Properties to set
         * @returns {common.KickOutUserResponse} KickOutUserResponse instance
         */
        KickOutUserResponse.create = function create(properties) {
            return new KickOutUserResponse(properties);
        };

        /**
         * Encodes the specified KickOutUserResponse message. Does not implicitly {@link common.KickOutUserResponse.verify|verify} messages.
         * @function encode
         * @memberof common.KickOutUserResponse
         * @static
         * @param {common.IKickOutUserResponse} message KickOutUserResponse message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        KickOutUserResponse.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.reason != null && Object.hasOwnProperty.call(message, "reason"))
                writer.uint32(/* id 1, wireType 2 =*/10).string(message.reason);
            return writer;
        };

        /**
         * Encodes the specified KickOutUserResponse message, length delimited. Does not implicitly {@link common.KickOutUserResponse.verify|verify} messages.
         * @function encodeDelimited
         * @memberof common.KickOutUserResponse
         * @static
         * @param {common.IKickOutUserResponse} message KickOutUserResponse message or plain object to encode
         * @param {$protobuf.Writer} [writer] Writer to encode to
         * @returns {$protobuf.Writer} Writer
         */
        KickOutUserResponse.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };

        /**
         * Decodes a KickOutUserResponse message from the specified reader or buffer.
         * @function decode
         * @memberof common.KickOutUserResponse
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @param {number} [length] Message length if known beforehand
         * @returns {common.KickOutUserResponse} KickOutUserResponse
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        KickOutUserResponse.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.common.KickOutUserResponse();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1: {
                        message.reason = reader.string();
                        break;
                    }
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };

        /**
         * Decodes a KickOutUserResponse message from the specified reader or buffer, length delimited.
         * @function decodeDelimited
         * @memberof common.KickOutUserResponse
         * @static
         * @param {$protobuf.Reader|Uint8Array} reader Reader or buffer to decode from
         * @returns {common.KickOutUserResponse} KickOutUserResponse
         * @throws {Error} If the payload is not a reader or valid buffer
         * @throws {$protobuf.util.ProtocolError} If required fields are missing
         */
        KickOutUserResponse.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };

        /**
         * Verifies a KickOutUserResponse message.
         * @function verify
         * @memberof common.KickOutUserResponse
         * @static
         * @param {Object.<string,*>} message Plain object to verify
         * @returns {string|null} `null` if valid, otherwise the reason why it is not
         */
        KickOutUserResponse.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (message.reason != null && message.hasOwnProperty("reason"))
                if (!$util.isString(message.reason))
                    return "reason: string expected";
            return null;
        };

        /**
         * Creates a KickOutUserResponse message from a plain object. Also converts values to their respective internal types.
         * @function fromObject
         * @memberof common.KickOutUserResponse
         * @static
         * @param {Object.<string,*>} object Plain object
         * @returns {common.KickOutUserResponse} KickOutUserResponse
         */
        KickOutUserResponse.fromObject = function fromObject(object) {
            if (object instanceof $root.common.KickOutUserResponse)
                return object;
            var message = new $root.common.KickOutUserResponse();
            if (object.reason != null)
                message.reason = String(object.reason);
            return message;
        };

        /**
         * Creates a plain object from a KickOutUserResponse message. Also converts values to other types if specified.
         * @function toObject
         * @memberof common.KickOutUserResponse
         * @static
         * @param {common.KickOutUserResponse} message KickOutUserResponse
         * @param {$protobuf.IConversionOptions} [options] Conversion options
         * @returns {Object.<string,*>} Plain object
         */
        KickOutUserResponse.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults)
                object.reason = "";
            if (message.reason != null && message.hasOwnProperty("reason"))
                object.reason = message.reason;
            return object;
        };

        /**
         * Converts this KickOutUserResponse to JSON.
         * @function toJSON
         * @memberof common.KickOutUserResponse
         * @instance
         * @returns {Object.<string,*>} JSON object
         */
        KickOutUserResponse.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };

        /**
         * Gets the default type url for KickOutUserResponse
         * @function getTypeUrl
         * @memberof common.KickOutUserResponse
         * @static
         * @param {string} [typeUrlPrefix] your custom typeUrlPrefix(default "type.googleapis.com")
         * @returns {string} The default type url
         */
        KickOutUserResponse.getTypeUrl = function getTypeUrl(typeUrlPrefix) {
            if (typeUrlPrefix === undefined) {
                typeUrlPrefix = "type.googleapis.com";
            }
            return typeUrlPrefix + "/common.KickOutUserResponse";
        };

        return KickOutUserResponse;
    })();

    return common;
})();

export default $root;
