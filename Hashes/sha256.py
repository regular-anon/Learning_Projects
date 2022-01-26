# https://qvault.io/cryptography/how-sha-2-works-step-by-step-sha-256/

#Get binary data of input
#from eth_utils import to_bytes


data = bytearray(input("Text: ").encode())
data.append(0b10000000)
messageLength = len(data) * 8 - 8

#Append '1' and add 0s until multiple of 512
# nextMultiple = 512
# while nextMultiple <= len(data) * 8:
#     nextMultiple += 512

# messageLength = len(data) * 8
# lengthBytes = messageLength.to_bytes(8, 'big')

# print(lengthBytes[len(lengthBytes) - 1])

multiple = 512
while len(data) * 8 + 64 > multiple:
    multiple += 512
# for i in range(0, multiple // 8 - len(data) - 8):
#     data.append(0b0)

while len(data) * 8 < multiple - 64:
    data.append(0b0)


lengthBytes = (messageLength).to_bytes(8, 'big')

for i in range(0, 8):
    data.append(lengthBytes[i])

# for i in range(0, len(data)):
#     print(data[i])

# print("Chunk count: " + str(len(data) // 64))
# print("Total length (bytes): " + str(len(data)))
# print("Total length (bits): " + str(len(data) * 8))
# print("Multiple: " + str(multiple))

#Initial hash values (h0 -> h7)
h0 = 0x6a09e667
h1 = 0xbb67ae85
h2 = 0x3c6ef372
h3 = 0xa54ff53a
h4 = 0x510e527f
h5 = 0x9b05688c
h6 = 0x1f83d9ab
h7 = 0x5be0cd19

roundValues = [0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5, 0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174, 0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da, 0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967, 0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85, 0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070, 0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3, 0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2]

testVar = b'\x00\x00\x00\x00'
testInt = int.from_bytes(testVar, 'big') + 1
print(testInt >> 1)

#Chunk Loop
for i in range(0, len(data) // 64):
    messageSchedule = []
    for j in range(0, 63, 4):
        word = data[i + j] * 2 ** 24 + data[i + j + 1] * 2 ** 16 + data[i + j + 2] * 2 ** 8 + data[i + j + 3]
        messageSchedule.append(word)
    for i in range(0, 48):
        messageSchedule.append(b'\x00\x00\x00\x00')
    # print(messageSchedule)
    # print("MS Length: " + str(len(messageSchedule)))
    #