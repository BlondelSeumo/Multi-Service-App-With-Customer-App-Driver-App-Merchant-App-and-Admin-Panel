<?php


class Email_model extends CI_model
{
	function emailsend($subject, $emailuser, $content, $host, $port, $username, $password, $from, $appname, $secure)
	{
		require APPPATH . '/libraries/class.phpmailer.php';
		$mail = new PHPMailer;
		$mail->IsSMTP();
		$mail->SMTPSecure = $secure;
		$mail->Host = $host; //host masing2 provider email
		$mail->SMTPDebug = 0;
		$mail->Port = $port;
		$mail->SMTPAuth = true;
		$mail->Username = $username; //user email
		$mail->Password = $password; //password email 
		$mail->SetFrom($from, $appname); //set email pengirim
		$mail->Subject = $subject; //subyek email
		$mail->AddAddress($emailuser, "User");  //tujuan email
		$mail->MsgHTML($content); //pesan dapat berupa html
		return $mail->Send();
	}

	function template2($subject, $emailmessage, $address, $appname, $linkgoogle, $web)
	{

		$msg = '<html xmlns="http://www.w3.org/1999/xhtml" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:v="urn:schemas-microsoft-com:vml">
		<head>
		<!--[if gte mso 9]><xml><o:OfficeDocumentSettings><o:AllowPNG/><o:PixelsPerInch>96</o:PixelsPerInch></o:OfficeDocumentSettings></xml><![endif]-->
		<meta content="text/html; charset=utf-8" http-equiv="Content-Type"/>
		<meta content="width=device-width" name="viewport"/>
		<!--[if !mso]><!-->
		<meta content="IE=edge" http-equiv="X-UA-Compatible"/>
		<!--<![endif]-->
		<title></title>
		<!--[if !mso]><!-->
		<!--<![endif]-->
		<style type="text/css">
				body {
					margin: 0;
					padding: 0;
				}
		
				table,
				td,
				tr {
					vertical-align: top;
					border-collapse: collapse;
				}
		
				* {
					line-height: inherit;
				}
		
				a[x-apple-data-detectors=true] {
					color: inherit !important;
					text-decoration: none !important;
				}
			</style>
		<style id="media-query" type="text/css">
				@media (max-width: 500px) {
		
					.block-grid,
					.col {
						min-width: 320px !important;
						max-width: 100% !important;
						display: block !important;
					}
		
					.block-grid {
						width: 100% !important;
					}
		
					.col {
						width: 100% !important;
					}
		
					.col>div {
						margin: 0 auto;
					}
		
					img.fullwidth,
					img.fullwidthOnMobile {
						max-width: 100% !important;
					}
		
					.no-stack .col {
						min-width: 0 !important;
						display: table-cell !important;
					}
		
					.no-stack.two-up .col {
						width: 50% !important;
					}
		
					.no-stack .col.num4 {
						width: 33% !important;
					}
		
					.no-stack .col.num8 {
						width: 66% !important;
					}
		
					.no-stack .col.num4 {
						width: 33% !important;
					}
		
					.no-stack .col.num3 {
						width: 25% !important;
					}
		
					.no-stack .col.num6 {
						width: 50% !important;
					}
		
					.no-stack .col.num9 {
						width: 75% !important;
					}
		
					.video-block {
						max-width: none !important;
					}
		
					.mobile_hide {
						min-height: 0px;
						max-height: 0px;
						max-width: 0px;
						display: none;
						overflow: hidden;
						font-size: 0px;
					}
		
					.desktop_hide {
						display: block !important;
						max-height: none !important;
					}
				}
			</style>
		</head>
		<body class="clean-body" style="margin: 0; padding: 0; -webkit-text-size-adjust: 100%; background-color: #FFFFFF;">
		<!--[if IE]><div class="ie-browser"><![endif]-->
		<table bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" class="nl-container" role="presentation" style="table-layout: fixed; vertical-align: top; min-width: 320px; Margin: 0 auto; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #FFFFFF; width: 100%;" valign="top" width="100%">
		<tbody>
		<tr style="vertical-align: top;" valign="top">
		<td style="word-break: break-word; vertical-align: top;" valign="top">
		<!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td align="center" style="background-color:#FFFFFF"><![endif]-->
		<div style="background-color:transparent;">
		<div class="block-grid" style="Margin: 0 auto; min-width: 320px; max-width: 480px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: transparent;">
		<div style="border-collapse: collapse;display: table;width: 100%;background-color:transparent;">
		<!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0" style="background-color:transparent;"><tr><td align="center"><table cellpadding="0" cellspacing="0" border="0" style="width:480px"><tr class="layout-full-width" style="background-color:transparent"><![endif]-->
		<!--[if (mso)|(IE)]><td align="center" width="480" style="background-color:transparent;width:480px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;" valign="top"><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;"><![endif]-->
		<div class="col num12" style="min-width: 320px; max-width: 480px; display: table-cell; vertical-align: top; width: 480px;">
		<div style="width:100% !important;">
		<!--[if (!mso)&(!IE)]><!-->
		<div style="border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;">
		<!--<![endif]-->
		<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif"><![endif]-->
		<div style="color:#555555;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;">
		<div style="line-height: 1.2; font-size: 12px; color: #555555; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 14px;">
		<p style="font-size: 28px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 34px; margin: 0;"><span style="font-size: 28px;"><strong>' . $subject . '</strong></span></p>
		</div>
		</div>
		<!--[if mso]></td></tr></table><![endif]-->
		<table border="0" cellpadding="0" cellspacing="0" class="divider" role="presentation" style="table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;" valign="top" width="100%">
		<tbody>
		<tr style="vertical-align: top;" valign="top">
		<td class="divider_inner" style="word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px;" valign="top">
		<table align="left" border="0" cellpadding="0" cellspacing="0" class="divider_content" role="presentation" style="table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-top: 8px solid #02AC43; width: 70%;" valign="top" width="70%">
		<tbody>
		<tr style="vertical-align: top;" valign="top">
		<td style="word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;" valign="top"><span></span></td>
		</tr>
		</tbody>
		</table>
		</td>
		</tr>
		</tbody>
		</table>
		<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif"><![endif]-->
		<div style="color:#555555;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;">
		<div style="line-height: 1.2; font-size: 12px; color: #555555; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 14px;">
		<p style="font-size: 14px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 17px; margin: 0;">' . $emailmessage . '</p>
		</div>
		</div>
		<!--[if mso]></td></tr></table><![endif]-->
		<!--[if (!mso)&(!IE)]><!-->
		</div>
		<!--<![endif]-->
		</div>
		</div>
		<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
		<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->
		</div>
		</div>
		</div>
		<div style="background-color:transparent;">
		<div class="block-grid two-up" style="Margin: 0 auto; min-width: 320px; max-width: 480px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: transparent;">
		<div style="border-collapse: collapse;display: table;width: 100%;background-color:transparent;">
		<!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0" style="background-color:transparent;"><tr><td align="center"><table cellpadding="0" cellspacing="0" border="0" style="width:480px"><tr class="layout-full-width" style="background-color:transparent"><![endif]-->
		<!--[if (mso)|(IE)]><td align="center" width="240" style="background-color:transparent;width:240px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;" valign="top"><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;"><![endif]-->
		<div class="col num6" style="max-width: 320px; min-width: 240px; display: table-cell; vertical-align: top; width: 240px;">
		<div style="width:100% !important;">
		<!--[if (!mso)&(!IE)]><!-->
		<div style="border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;">
		<!--<![endif]-->
		<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 15px; padding-left: 15px; padding-top: 30px; padding-bottom: 30px; font-family: Arial, sans-serif"><![endif]-->
		<div style="color:#555555;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:30px;padding-right:15px;padding-bottom:30px;padding-left:15px;">
		<div style="line-height: 1.2; font-size: 12px; color: #555555; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 14px;">
		<p style="font-size: 14px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 17px; margin: 0;">' . $address . '</p>
		</div>
		</div>
		<!--[if mso]></td></tr></table><![endif]-->
		<!--[if (!mso)&(!IE)]><!-->
		</div>
		<!--<![endif]-->
		</div>
		</div>
		<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
		<!--[if (mso)|(IE)]></td><td align="center" width="240" style="background-color:transparent;width:240px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;" valign="top"><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;"><![endif]-->
		<div class="col num6" style="max-width: 320px; min-width: 240px; display: table-cell; vertical-align: top; width: 240px;">
		<div style="width:100% !important;">
		<!--[if (!mso)&(!IE)]><!-->
		<div style="border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;">
		<!--<![endif]-->
		<div align="center" class="img-container center fixedwidth" style="padding-right: 0px;padding-left: 0px;">
		<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr style="line-height:0px"><td style="padding-right: 0px;padding-left: 0px;" align="center"><![endif]--><a href="' . $linkgoogle . '" style="outline:none" tabindex="-1" target="_blank"> <img align="center" alt="Alternate text" border="0" class="center fixedwidth" src="http://ourdevelops.com/fileenvato/googleplay.png" style="text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: none; width: 100%; max-width: 240px; display: block;" title="Alternate text" width="240"/></a>
		<!--[if mso]></td></tr></table><![endif]-->
		</div>
		<!--[if (!mso)&(!IE)]><!-->
		</div>
		<!--<![endif]-->
		</div>
		</div>
		<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
		<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->
		</div>
		</div>
		</div>
		<div style="background-color:transparent;">
		<div class="block-grid" style="Margin: 0 auto; min-width: 320px; max-width: 480px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: transparent;">
		<div style="border-collapse: collapse;display: table;width: 100%;background-color:transparent;">
		<!--[if (mso)|(IE)]><table width="100%" cellpadding="0" cellspacing="0" border="0" style="background-color:transparent;"><tr><td align="center"><table cellpadding="0" cellspacing="0" border="0" style="width:480px"><tr class="layout-full-width" style="background-color:transparent"><![endif]-->
		<!--[if (mso)|(IE)]><td align="center" width="480" style="background-color:transparent;width:480px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;" valign="top"><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;"><![endif]-->
		<div class="col num12" style="min-width: 320px; max-width: 480px; display: table-cell; vertical-align: top; width: 480px;">
		<div style="width:100% !important;">
		<!--[if (!mso)&(!IE)]><!-->
		<div style="border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;">
		<!--<![endif]-->
		<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr><td style="padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: Arial, sans-serif"><![endif]-->
		<div style="color:#555555;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;">
		<div style="line-height: 1.2; font-size: 12px; color: #555555; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 14px;">
		<p style="font-size: 14px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 17px; margin: 0;">Copyright © 2020 <a href="' . $web . '" rel="noopener" style="text-decoration: underline; color: #0068A5;" target="_blank">' . $appname . '</a>. All Rights Reserved</p>
		</div>
		</div>
		<!--[if mso]></td></tr></table><![endif]-->
		<div align="center" class="img-container center fixedwidth" style="padding-right: 0px;padding-left: 0px;">
		<!--[if mso]><table width="100%" cellpadding="0" cellspacing="0" border="0"><tr style="line-height:0px"><td style="padding-right: 0px;padding-left: 0px;" align="center"><![endif]--><a href="' . $web . '" style="outline:none" tabindex="-1" target="_blank"> </a>
		<!--[if mso]></td></tr></table><![endif]-->
		</div>
		<!--[if (!mso)&(!IE)]><!-->
		</div>
		<!--<![endif]-->
		</div>
		</div>
		<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
		<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->
		</div>
		</div>
		</div>
		<!--[if (mso)|(IE)]></td></tr></table><![endif]-->
		</td>
		</tr>
		</tbody>
		</table>
		<!--[if (IE)]></div><![endif]-->
		</body>
		</html>';
		return $msg;
	}
}
