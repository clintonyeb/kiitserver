package com.clinton.kiitsocial

import com.clinton.auth.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.transaction.Transactional
import org.springframework.web.multipart.MultipartFile

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

import static org.imgscalr.Scalr.*

@Transactional
class AvatarService {

    static final int AVATAR_TINY = 80
    static final int AVATAR_SMALL = 150
    static final int AVATAR_MEDIUM = 300

    SpringSecurityService springSecurityService
    static final acceptedAvatarTypes = ['image/png', 'image/jpeg', 'image/gif']
    public static int responseCode = 200

    def showAvatar(long id, response) {
        def avatar = Avatar.get(id)
        if (!avatar) {
            throw new IllegalArgumentException()
        }
        responseCode = 200
        response.setHeader('Content-length', avatar.avatarBytes.size())
        response.contentType = avatar.avatarType
        response.contentLength = avatar.avatarBytes.size()
        OutputStream out = response.outputStream
        out.write(avatar.avatarBytes)
        out.close()
    }

    def createAvatar(MultipartFile file, int size) {
        User user = (User) springSecurityService.currentUser
        //File image
        Avatar smallAvatar = new Avatar()
        smallAvatar.avatarName = file.originalFilename
        smallAvatar.avatarType = file.contentType
        smallAvatar.avatarBytes = resizeAvatar(fromBufferedImageToByte(ImageIO.read(file.inputStream),
                smallAvatar.avatarType), smallAvatar.avatarType, size)
        if (smallAvatar.save())
            user.smallAvatar = smallAvatar
    }

    def createAvatar(GenderList gender) {
        InputStream image
        Avatar smallAvatar = new Avatar()
        smallAvatar.avatarName = "default"
        smallAvatar.avatarType = "png"
        if (gender == GenderList.FEMALE)
            image =  this.class.getResourceAsStream('/images/fem-avatar-tiny.png')
        else
            image =  this.class.getResourceAsStream('/images/male-avatar-tiny.png')
        assert image
        smallAvatar.avatarBytes = resizeAvatar(fromBufferedImageToByte(ImageIO.read(image),
                smallAvatar.avatarType), smallAvatar.avatarType, AVATAR_TINY)
        smallAvatar
    }

    def resizeAvatar(byte[] image, String cont, int size) {
        fromBufferedImageToByte(resize(fromByteToBufferedImage(image, cont),
                Method.QUALITY, Mode.AUTOMATIC, size, OP_ANTIALIAS), cont)
    }

    def fromBufferedImageToByte(BufferedImage bufferedImage, String contentType) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ImageIO.write(bufferedImage, contentType, baos)
        baos.toByteArray()
    }

    def fromByteToBufferedImage(byte[] image, String contentType) {
        ByteArrayInputStream out = new ByteArrayInputStream(image)
        ImageIO.read(out)
    }

    String decodeByteToString(byte[] image) {
        image.encodeBase64().toString()
    }

    def encodeStringToByte(String decoded) {
        new String(decoded.decodeBase64())
    }
}
